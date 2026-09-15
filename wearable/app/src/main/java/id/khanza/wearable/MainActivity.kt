package id.khanza.wearable

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var hc: HealthConnectManager
    private lateinit var prefs: android.content.SharedPreferences

    private lateinit var inputUrl: EditText
    private lateinit var inputToken: EditText
    private lateinit var inputMenit: EditText
    private lateinit var teksStatus: TextView
    private lateinit var wadahCheckbox: LinearLayout

    private val checkboxMap = LinkedHashMap<String, CheckBox>()

    private val daftarVariabel = linkedMapOf(
        "heartRate" to "Detak Jantung",
        "restingHeartRate" to "Detak Jantung Istirahat",
        "heartRateVariabilitySDNN" to "Variabilitas Detak Jantung",
        "oxygenSaturation" to "Saturasi Oksigen (SpO2)",
        "bloodPressureSystolic" to "Tekanan Darah Sistolik",
        "bloodPressureDiastolic" to "Tekanan Darah Diastolik",
        "respiratoryRate" to "Laju Napas",
        "bodyTemperature" to "Suhu Tubuh",
        "basalBodyTemperature" to "Suhu Basal Tubuh",
        "bloodGlucose" to "Glukosa Darah",
        "bodyMass" to "Berat Badan",
        "height" to "Tinggi Badan",
        "bodyFatPercentage" to "Persentase Lemak Tubuh",
        "stepCount" to "Jumlah Langkah",
        "distanceWalkingRunning" to "Jarak Jalan/Lari",
        "activeEnergyBurned" to "Kalori Aktif",
        "basalEnergyBurned" to "Kalori Basal",
        "flightsClimbed" to "Lantai Dinaiki",
        "vo2Max" to "VO2 Max"
    )

    private val mintaIzin =
        registerForActivityResult(PermissionController.createRequestPermissionResultContract()) { granted ->
            if (granted.containsAll(hc.izinDibutuhkan)) {
                teksStatus.text = "Status: izin diberikan. Silakan tekan tombol 2."
            } else {
                teksStatus.text = "Status: sebagian izin belum diberikan."
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hc = HealthConnectManager(this)
        prefs = getSharedPreferences("wearable", Context.MODE_PRIVATE)

        inputUrl = findViewById(R.id.inputUrl)
        inputToken = findViewById(R.id.inputToken)
        inputMenit = findViewById(R.id.inputMenit)
        teksStatus = findViewById(R.id.teksStatus)
        wadahCheckbox = findViewById(R.id.wadahCheckbox)

        inputUrl.setText(prefs.getString("url", ""))
        inputToken.setText(prefs.getString("token", ""))
        val menitTersimpan = prefs.getInt("menit", 0)
        inputMenit.setText(if (menitTersimpan > 0) menitTersimpan.toString() else "")

        val terpilih = prefs.getStringSet("variabel", daftarVariabel.keys)!!
        for ((key, label) in daftarVariabel) {
            val cb = CheckBox(this)
            cb.text = label
            cb.isChecked = key in terpilih
            wadahCheckbox.addView(cb)
            checkboxMap[key] = cb
        }

        findViewById<Button>(R.id.btnSimpan).setOnClickListener {
            val dipilih = checkboxMap.filter { it.value.isChecked }.keys
            val menit = inputMenit.text.toString().trim().toIntOrNull() ?: 0
            prefs.edit()
                .putString("url", inputUrl.text.toString().trim())
                .putString("token", inputToken.text.toString().trim())
                .putStringSet("variabel", dipilih)
                .putInt("menit", menit)
                .apply()

            aturJadwal(menit)

            if (menit <= 0) {
                teksStatus.text = "Status: pengaturan tersimpan. Pengiriman otomatis DIMATIKAN."
            } else {
                val efektif = if (menit < 15) 15 else menit
                teksStatus.text = "Status: pengaturan tersimpan. Kirim otomatis tiap $efektif menit." +
                        (if (menit < 15) " (minimum Android 15 menit)" else "")
            }
        }

        findViewById<Button>(R.id.btnIzin).setOnClickListener {
            cekHealthConnect { mintaIzin.launch(hc.izinDibutuhkan) }
        }

        findViewById<Button>(R.id.btnKirim).setOnClickListener {
            val url = inputUrl.text.toString().trim()
            val token = inputToken.text.toString().trim()
            if (url.isEmpty() || token.isEmpty()) {
                teksStatus.text = "Status: URL dan Token wajib diisi dulu."
                return@setOnClickListener
            }
            cekHealthConnect { bacaDanKirim(url, token) }
        }
    }

    private fun aturJadwal(menit: Int) {
        val wm = WorkManager.getInstance(applicationContext)
        if (menit <= 0) {
            wm.cancelUniqueWork("kirim_wearable")
            return
        }
        val interval = if (menit < 15) 15L else menit.toLong()
        val req = PeriodicWorkRequestBuilder<KirimWorker>(interval, TimeUnit.MINUTES).build()
        wm.enqueueUniquePeriodicWork(
            "kirim_wearable",
            ExistingPeriodicWorkPolicy.UPDATE,
            req
        )
    }

    private fun cekHealthConnect(lanjut: () -> Unit) {
        val status = HealthConnectClient.getSdkStatus(this)
        if (status != HealthConnectClient.SDK_AVAILABLE) {
            teksStatus.text = "Status: Health Connect belum tersedia/aktif di HP ini."
            return
        }
        lanjut()
    }

    private fun bacaDanKirim(url: String, token: String) {
        val dipilih = checkboxMap.filter { it.value.isChecked }.keys.toSet()
        if (dipilih.isEmpty()) {
            teksStatus.text = "Status: pilih minimal satu variabel."
            return
        }
        teksStatus.text = "Status: membaca data..."
        lifecycleScope.launch {
            if (!hc.sudahAdaIzin()) {
                teksStatus.text = "Status: izin belum lengkap. Tekan tombol 1 dulu."
                return@launch
            }
            val data = hc.bacaData(dipilih)
            if (data.isEmpty()) {
                teksStatus.text = "Status: tidak ada data pada 24 jam terakhir."
                return@launch
            }
            teksStatus.text = "Status: mengirim ${data.size} data..."
            val hasil = withContext(Dispatchers.IO) { hc.kirim(url, token, data) }
            teksStatus.text = "Terkirim ${data.size} data.\nRespon server:\n$hasil"
        }
    }
}
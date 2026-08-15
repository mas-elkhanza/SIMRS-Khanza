package id.khanza.wearable

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var hc: HealthConnectManager
    private lateinit var prefs: android.content.SharedPreferences

    private lateinit var inputUrl: EditText
    private lateinit var inputToken: EditText
    private lateinit var teksStatus: TextView
    private lateinit var wadahCheckbox: LinearLayout

    private val checkboxMap = LinkedHashMap<String, CheckBox>()

    // daftar variabel: key JSON -> label tampilan
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

    // launcher minta izin Health Connect
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
        teksStatus = findViewById(R.id.teksStatus)
        wadahCheckbox = findViewById(R.id.wadahCheckbox)

        // muat pengaturan tersimpan
        inputUrl.setText(prefs.getString("url", ""))
        inputToken.setText(prefs.getString("token", ""))

        // buat checkbox variabel
        val terpilih = prefs.getStringSet("variabel", daftarVariabel.keys)!!
        for ((key, label) in daftarVariabel) {
            val cb = CheckBox(this)
            cb.text = label
            cb.isChecked = key in terpilih
            wadahCheckbox.addView(cb)
            checkboxMap[key] = cb
        }

        // tombol simpan pengaturan
        findViewById<Button>(R.id.btnSimpan).setOnClickListener {
            val dipilih = checkboxMap.filter { it.value.isChecked }.keys
            prefs.edit()
                .putString("url", inputUrl.text.toString().trim())
                .putString("token", inputToken.text.toString().trim())
                .putStringSet("variabel", dipilih)
                .apply()
            teksStatus.text = "Status: pengaturan tersimpan."
        }

        // tombol 1: minta izin
        findViewById<Button>(R.id.btnIzin).setOnClickListener {
            cekHealthConnect { mintaIzin.launch(hc.izinDibutuhkan) }
        }

        // tombol 2: baca & kirim
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

    // pastikan Health Connect tersedia sebelum aksi
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
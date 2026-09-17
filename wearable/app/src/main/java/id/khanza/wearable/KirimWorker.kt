package id.khanza.wearable

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class KirimWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        android.util.Log.d("KirimWorker", "doWork MULAI")
        val prefs = applicationContext.getSharedPreferences("wearable", Context.MODE_PRIVATE)
        val url = prefs.getString("url", "") ?: ""
        val token = prefs.getString("token", "") ?: ""
        if (url.isEmpty() || token.isEmpty()) {
            android.util.Log.d("KirimWorker", "STOP: url/token kosong")
            return Result.success()
        }

        val hc = HealthConnectManager(applicationContext)

        if (!hc.sudahAdaIzin()) {
            android.util.Log.d("KirimWorker", "STOP: izin belum lengkap")
            return Result.success()
        }

        val semua = setOf(
            "heartRate","restingHeartRate","heartRateVariabilitySDNN","oxygenSaturation",
            "bloodPressureSystolic","bloodPressureDiastolic","respiratoryRate","bodyTemperature",
            "basalBodyTemperature","bloodGlucose","bodyMass","height","bodyFatPercentage",
            "stepCount","distanceWalkingRunning","activeEnergyBurned","basalEnergyBurned",
            "flightsClimbed","vo2Max"
        )
        val dipilih = prefs.getStringSet("variabel", semua) ?: semua

        return try {
            val data = hc.bacaData(dipilih)
            android.util.Log.d("KirimWorker", "data terbaca: " + data.size + " item")
            if (data.isNotEmpty()) {
                val hasil = hc.kirim(url, token, data)
                android.util.Log.d("KirimWorker", "respon server: " + hasil)
            }
            Result.success()
        } catch (e: Exception) {
            android.util.Log.d("KirimWorker", "ERROR: " + e.message)
            Result.retry()
        }
    }
}
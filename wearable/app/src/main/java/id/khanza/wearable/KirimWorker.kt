package id.khanza.wearable

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class KirimWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val prefs = applicationContext.getSharedPreferences("wearable", Context.MODE_PRIVATE)
        val url = prefs.getString("url", "") ?: ""
        val token = prefs.getString("token", "") ?: ""
        if (url.isEmpty() || token.isEmpty()) {
            return Result.success()
        }

        val hc = HealthConnectManager(applicationContext)

        if (!hc.sudahAdaIzin()) {
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
            if (data.isNotEmpty()) {
                hc.kirim(url, token, data)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
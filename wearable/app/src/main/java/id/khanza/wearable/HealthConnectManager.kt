package id.khanza.wearable

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

class HealthConnectManager(private val context: Context) {

    val client: HealthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }
    val izinDibutuhkan = setOf(
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(RestingHeartRateRecord::class),
        HealthPermission.getReadPermission(HeartRateVariabilityRmssdRecord::class),
        HealthPermission.getReadPermission(OxygenSaturationRecord::class),
        HealthPermission.getReadPermission(BloodPressureRecord::class),
        HealthPermission.getReadPermission(RespiratoryRateRecord::class),
        HealthPermission.getReadPermission(BodyTemperatureRecord::class),
        HealthPermission.getReadPermission(BasalBodyTemperatureRecord::class),
        HealthPermission.getReadPermission(BloodGlucoseRecord::class),
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(HeightRecord::class),
        HealthPermission.getReadPermission(BodyFatRecord::class),
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(DistanceRecord::class),
        HealthPermission.getReadPermission(ActiveCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(FloorsClimbedRecord::class),
        HealthPermission.getReadPermission(Vo2MaxRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class)
    )

    suspend fun sudahAdaIzin(): Boolean {
        val granted = client.permissionController.getGrantedPermissions()
        return granted.containsAll(izinDibutuhkan)
    }

    private fun rentang24Jam(): TimeRangeFilter {
        val akhir = Instant.now()
        val awal = akhir.minus(1, ChronoUnit.DAYS)
        return TimeRangeFilter.between(awal, akhir)
    }

    private suspend inline fun <reified T : Record> ambilTerakhir(): T? {
        return try {
            client.readRecords(
                ReadRecordsRequest(T::class, timeRangeFilter = rentang24Jam())
            ).records.lastOrNull()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun bacaData(pilihan: Set<String>): Map<String, String> {
        val data = LinkedHashMap<String, String>()

        if ("heartRate" in pilihan)
            (ambilTerakhir<HeartRateRecord>())?.samples?.lastOrNull()?.let {
                data["heartRate"] = it.beatsPerMinute.toString()
            }

        if ("restingHeartRate" in pilihan)
            (ambilTerakhir<RestingHeartRateRecord>())?.let {
                data["restingHeartRate"] = it.beatsPerMinute.toString()
            }

        if ("heartRateVariabilitySDNN" in pilihan)
            (ambilTerakhir<HeartRateVariabilityRmssdRecord>())?.let {
                data["heartRateVariabilitySDNN"] = it.heartRateVariabilityMillis.toString()
            }

        if ("oxygenSaturation" in pilihan)
            (ambilTerakhir<OxygenSaturationRecord>())?.let {
                // Health Connect: percentage sudah 0-100, TIDAK perlu x100
                data["oxygenSaturation"] = it.percentage.value.toString()
            }

        if ("bloodPressureSystolic" in pilihan || "bloodPressureDiastolic" in pilihan)
            (ambilTerakhir<BloodPressureRecord>())?.let {
                if ("bloodPressureSystolic" in pilihan)
                    data["bloodPressureSystolic"] = it.systolic.inMillimetersOfMercury.toInt().toString()
                if ("bloodPressureDiastolic" in pilihan)
                    data["bloodPressureDiastolic"] = it.diastolic.inMillimetersOfMercury.toInt().toString()
            }

        if ("respiratoryRate" in pilihan)
            (ambilTerakhir<RespiratoryRateRecord>())?.let {
                data["respiratoryRate"] = it.rate.toString()
            }

        if ("bodyTemperature" in pilihan)
            (ambilTerakhir<BodyTemperatureRecord>())?.let {
                data["bodyTemperature"] = it.temperature.inCelsius.toString()
            }

        if ("basalBodyTemperature" in pilihan)
            (ambilTerakhir<BasalBodyTemperatureRecord>())?.let {
                data["basalBodyTemperature"] = it.temperature.inCelsius.toString()
            }

        if ("bloodGlucose" in pilihan)
            (ambilTerakhir<BloodGlucoseRecord>())?.let {
                data["bloodGlucose"] = it.level.inMilligramsPerDeciliter.toInt().toString()
            }

        if ("bodyMass" in pilihan)
            (ambilTerakhir<WeightRecord>())?.let {
                data["bodyMass"] = it.weight.inKilograms.toString()
            }

        if ("height" in pilihan)
            (ambilTerakhir<HeightRecord>())?.let {
                data["height"] = (it.height.inMeters * 100).toString() // meter -> cm
            }

        if ("bodyFatPercentage" in pilihan)
            (ambilTerakhir<BodyFatRecord>())?.let {
                data["bodyFatPercentage"] = it.percentage.value.toString()
            }

        if ("stepCount" in pilihan)
            (ambilTerakhir<StepsRecord>())?.let {
                data["stepCount"] = it.count.toString()
            }

        if ("distanceWalkingRunning" in pilihan)
            (ambilTerakhir<DistanceRecord>())?.let {
                data["distanceWalkingRunning"] = it.distance.inMeters.toInt().toString()
            }

        if ("activeEnergyBurned" in pilihan)
            (ambilTerakhir<ActiveCaloriesBurnedRecord>())?.let {
                data["activeEnergyBurned"] = it.energy.inKilocalories.toInt().toString()
            }

        if ("basalEnergyBurned" in pilihan)
            (ambilTerakhir<TotalCaloriesBurnedRecord>())?.let {
                data["basalEnergyBurned"] = it.energy.inKilocalories.toInt().toString()
            }

        if ("flightsClimbed" in pilihan)
            (ambilTerakhir<FloorsClimbedRecord>())?.let {
                data["flightsClimbed"] = it.floors.toInt().toString()
            }

        if ("vo2Max" in pilihan)
            (ambilTerakhir<Vo2MaxRecord>())?.let {
                data["vo2Max"] = it.vo2MillilitersPerMinuteKilogram.toString()
            }

        return data
    }

    fun kirim(url: String, token: String, data: Map<String, String>): String {
        val tanggal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
        val sb = StringBuilder()
        sb.append("{")
        sb.append("\"tanggal\":\"").append(tanggal).append("\"")
        for ((k, v) in data) {
            sb.append(",\"").append(k).append("\":").append(v)
        }
        sb.append("}")
        val json = sb.toString()

        val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(url)
            .addHeader("token", token)
            .post(body)
            .build()

        return try {
            OkHttpClient().newCall(request).execute().use { resp ->
                resp.body?.string() ?: "Tidak ada respon"
            }
        } catch (e: Exception) {
            "Gagal kirim: ${e.message}"
        }
    }
}
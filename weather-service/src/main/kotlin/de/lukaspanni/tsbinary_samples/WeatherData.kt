import io.ktor.util.*
import kotlinx.datetime.LocalDateTime;
import kotlinx.datetime.toJavaLocalDateTime
import java.nio.ByteBuffer
import java.time.ZoneOffset

@kotlinx.serialization.Serializable()
data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val timestamp: LocalDateTime,
    val temperature: Float,
    val humidity: Float
)

val exampleData = WeatherData(
    49.1779916,
    8.7468315,
    LocalDateTime(2022, 6, 12, 14, 37, 42),
    21.5f,
    60.25f
)

fun Float.bytes(): ByteArray =
    ByteBuffer.allocate(Float.SIZE_BYTES)
        .putInt(java.lang.Float.floatToIntBits(this)).position(0).moveToByteArray()

fun Double.bytes(): ByteArray =
    ByteBuffer.allocate(Double.SIZE_BYTES)
        .putLong(java.lang.Double.doubleToLongBits(this)).position(0).moveToByteArray()


fun Int.bytes(): ByteArray =
    ByteBuffer.allocate(Int.SIZE_BYTES)
        .putInt(this).position(0).moveToByteArray()

fun Long.bytes(): ByteArray =
    ByteBuffer.allocate(Long.SIZE_BYTES).putLong(this).position(0).moveToByteArray()

fun getExampleDataBinaryProtocol(realistic: Boolean = true): ByteArray {
    // very simple - just binary:
    // Latitude | Longitude | Timestamp (as ticks) | Temperature | Humidity
    // realistic protocol:
    // Type (e.g. 1 Byte) | Length | Latitude | Longitude | Timestamp (as ticks) | Temperature | Humidity

    var bytes: ByteArray
    var index = 0;

    if (realistic) {
        bytes = ByteArray(41)
        bytes[index++] = 0x81.toByte() // type = WeatherResponse (0x80 (=Response) & 0x01 (=WeatherData))
        val lengthBytes = bytes.size.bytes()
        lengthBytes.forEach { byte -> bytes[index++] = byte }
    } else {
        bytes = ByteArray(40)
    }

    val latBytes = exampleData.latitude.bytes()
    latBytes.forEach { byte -> bytes[index++] = byte }
    val longBytes = exampleData.longitude.bytes()
    longBytes.forEach { byte -> bytes[index++] = byte }
    val timeBytes = exampleData.timestamp.toJavaLocalDateTime().toInstant(ZoneOffset.UTC).toEpochMilli().bytes()
    timeBytes.forEach { byte -> bytes[index++] = byte }
    val tempBytes = exampleData.temperature.bytes()
    tempBytes.forEach { byte -> bytes[index++] = byte }
    val humidityBytes = exampleData.humidity.bytes()
    humidityBytes.forEach { byte -> bytes[index++] = byte }

    return bytes
}
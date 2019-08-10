import com.soywiz.krypto.SHA256
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.tools.hex.hex
import kotlin.test.Test

class EncryptionTest {

    @Test
    fun shouldEncrypt() {
        var str = "andylamax"
        var encrypted = SHA256.digest(str.toUtf8Bytes()).hex
        println("Original: $str, Encoded: $encrypted")

        str = "Andylamax1"
        encrypted = SHA256.digest(str.toUtf8Bytes()).hex
        println("Original: $str, Encoded: $encrypted")
    }
}
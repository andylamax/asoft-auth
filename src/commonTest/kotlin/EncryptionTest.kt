import tz.co.asoft.krypto.SHA256
import tz.co.asoft.auth.tools.hex.hex
import kotlin.test.Test

class EncryptionTest {

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun shouldEncrypt() {
        var str = "andylamax"
        var encrypted = SHA256.digest(str.encodeToByteArray()).hex
        println("Original: $str, Encoded: $encrypted")

        str = "Andylamax1"
        encrypted = SHA256.digest(str.encodeToByteArray()).hex
        println("Original: $str, Encoded: $encrypted")
    }
}
import tz.co.asoft.auth.Email
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailTest {
    @Test
    fun shouldBeValid() {
        val email1 = Email("email@test.com")
        val email2 = Email("email.email@test.net")
        val email3 = Email("email.test@test.email.com")
        assertTrue { email1.isValid && email2.isValid && email3.isValid }
    }

    @Test
    fun shouldBeInvalid() {
        val email1 = Email("email@testcom")
        val email2 = Email("email.email@test")
        val email3 = Email("email.testemail.com")
        assertFalse { email1.isValid && email2.isValid && email3.isValid }
    }
}
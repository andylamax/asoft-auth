import tz.co.asoft.auth.Phone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PhoneTest {

    @Test
    fun shouldBeNormalized() {
        val phone1 = Phone("0752748674")
        assertEquals("255752748674", phone1.normalize().value)

        val phone2 = Phone("255752748674")
        assertEquals("255752748674", phone2.normalize().value)

        val phone3 = Phone("254752748674")
        assertEquals("254752748674", phone3.normalize().value)

        val phone4 = Phone("+255752748674")
        assertEquals("255752748674", phone4.normalize().value)

        val phone5 = Phone("+256752748674")
        assertEquals("256752748674", phone5.normalize().value)
    }

    @Test
    fun shouldBeValid() {
        val phone1 = Phone("+255752748674")
        assertTrue { phone1.isValid }

        val phone2 = Phone("0625682161")

        assertTrue { phone2.isValid }

        val phone3 = Phone(255000000000)
        assertTrue { phone3.isValid }
    }

    @Test
    fun shouldBeInvalid() {
        val phone1 = Phone("255")
        assertFalse { phone1.isValid }

        val phone2 = Phone("0000000000")
        assertFalse { phone2.isValid }
    }
}
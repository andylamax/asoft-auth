package hidden

import kotlinx.serialization.Serializable
import tz.co.asoft.email.Email

@Serializable
data class Person(val name: String, val email: String) {
    companion object {
        operator fun invoke(name: String, email: Email) = Person(name, email.value)
    }
}
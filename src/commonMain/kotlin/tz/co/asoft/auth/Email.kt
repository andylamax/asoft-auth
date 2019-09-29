package tz.co.asoft.auth

import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.auth.tools.name.asName

inline class Email(val value: String) {

    val isValid: Boolean
        get() {
            val parts = value.split("@")
            return parts.size == 2 && parts.getOrNull(1)?.contains(".") == true
        }

    val isNotValid get() = !isValid

    companion object {
        val fakeProviders get() = listOf("google.com", "yahoo.com", "mail.com", "people.com", "asofttz.com")
        fun fake(name: String? = null): String {
            val n = name?.asName()?.randomized() ?: Name.fake.asName()
            return "${n.last.replace("'", "")}.${n.first.replace("'", "")}@${fakeProviders.random()}".toLowerCase()
        }
    }
}
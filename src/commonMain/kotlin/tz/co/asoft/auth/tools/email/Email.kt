package tz.co.asoft.auth.tools.email

import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.auth.tools.name.asName

class Email {
    companion object {
        val fakeProviders get() = listOf("google.com", "yahoo.com", "mail.com", "people.com", "asofttz.com")
        fun fake(name: String? = null) : String {
            val n = name?.asName()?.randomized() ?: Name.fake.asName()
            return "${n.last.replace("'", "")}.${n.first.replace("'", "")}@${fakeProviders.random()}".toLowerCase()
        }
    }
}
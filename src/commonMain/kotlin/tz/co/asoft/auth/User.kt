package tz.co.asoft.auth

import com.soywiz.klock.DateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import tz.co.asoft.auth.tools.email.Email
import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.auth.tools.name.asName
import tz.co.asoft.auth.tools.phone.Phone

@Serializable
open class User {
    var uid = ""
    var name = ""
    var password = ""
    var username = ""
    var permits = mutableListOf(":settings", ":logs", ":profile")
    var scopes = mutableListOf<String>()
    var emails = mutableListOf<String>()
    var phones = mutableListOf<String>()
    var gender = Gender.Male.name
    var photoUrl: String = ""
    var isBlocked = false

    var registeredOn = DateTime.nowUnixLong()

    var lastSeen = DateTime.nowUnixLong()

    var lastModified = DateTime.nowUnixLong()

    enum class Gender {
        Male, Female
    }

    fun hasPermit(perm: String): Boolean {
        if (permits.contains(":dev")) {
            return true
        }
        return permits.indexOfFirst { it == perm } >= 0
    }

    fun hasPermits(perms: Array<String>): Boolean {
        var hasPerms = false
        perms.forEach { perm ->
            hasPerms = hasPermit(perm)
            if (hasPerms)
                return true
        }
        return hasPerms
    }

    @Deprecated("Try not to use this method at all", ReplaceWith("Nothing at the moment"))
    fun clone(): User = Json.parse(serializer(), Json.stringify(serializer(), this))

    companion object {
        val fake
            get() = User().apply {
                uid = ""
                name = Name.fake
                gender = Gender.values().random().name
                username = name.asName().first.toLowerCase()
                password = "1234"
                emails.add(Email.fake(name))
                phones.add(Phone.fake)
            }
    }
}
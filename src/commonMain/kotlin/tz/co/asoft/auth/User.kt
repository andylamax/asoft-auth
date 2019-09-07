package tz.co.asoft.auth

import com.soywiz.klock.DateTime
import kotlinx.serialization.Serializable
import tz.co.asoft.auth.tools.email.Email
import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.auth.tools.name.asName
import tz.co.asoft.auth.tools.phone.Phone
import tz.co.asoft.neo4j.Neo4JEntity
import tz.co.asoft.neo4j.annotations.GeneratedValue
import tz.co.asoft.neo4j.annotations.Id
import tz.co.asoft.neo4j.annotations.NodeEntity

@Serializable
@NodeEntity
open class User : Neo4JEntity {
    @Id
    @GeneratedValue
    override var id: Long? = null
    override var uid = ""
    var name = ""
    var password = ""
    var username = ""
    var permits = mutableListOf(":settings", ":logs", ":profile")
    var scopes = mutableListOf<String>()
    var emails = mutableListOf<String>()
    var phones = mutableListOf<String>()
    var photoUrl: String = ""
    var status = Status.SignedIn.name

    var registeredOn = DateTime.nowUnixLong()

    var lastSeen = DateTime.nowUnixLong()

    var lastModified = DateTime.nowUnixLong()

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

    enum class Status {
        Blocked,
        SignedIn,
        SignedOut
    }

    fun getRef() = UserRef(uid, name, photoUrl)

    companion object {
        val fake
            get() = User().apply {
                uid = ""
                name = Name.fake
                username = name.asName().first.toLowerCase()
                password = "123456"
                status = Status.values().random().name
                emails.add(Email.fake(name))
                phones.add(Phone.fake)
            }
    }
}
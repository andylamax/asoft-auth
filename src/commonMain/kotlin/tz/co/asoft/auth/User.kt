package tz.co.asoft.auth

import kotlinx.serialization.Serializable
import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.auth.tools.name.asName
import tz.co.asoft.email.Email
import tz.co.asoft.klock.DateTime
import tz.co.asoft.neo4j.annotations.GeneratedValue
import tz.co.asoft.neo4j.annotations.Id
import tz.co.asoft.neo4j.annotations.NodeEntity
import tz.co.asoft.phone.Phone

@Serializable
@NodeEntity
open class User : Claimer {
    @Id
    @GeneratedValue
    override var id: Long? = null
    override var uid = ""
    override var name = ""
    var password: String? = ""
    var username = ""
    @Deprecated("Use permissions instead")
    var permits = mutableListOf(":settings", ":logs", ":profile")
    var permissions = mutableListOf<Permission>()
    var scopes = mutableListOf<String>()
    var emails = mutableListOf<String>()
    var phones = mutableListOf<String>()
    var photoUrl = ""
    var status = Status.SignedIn.name
    var accounts = mutableListOf<UserAccount>()

    var verifiedEmails = mutableListOf<String>()
    var verifiedPhones = mutableListOf<String>()

    var registeredOn = DateTime.nowUnixLong()

    var lastSeen = DateTime.nowUnixLong()

    enum class Status {
        Blocked,
        SignedIn,
        SignedOut,
        Deleted
    }

    fun ref() = UserRef().also {
        it.uid = uid
        it.name = name
        it.photoUrl = photoUrl
    }

    companion object {
        val fake
            get() = User().apply {
                uid = ""
                name = Name.fake
                username = name.asName().first.toLowerCase()
                password = "123456"
                status = Status.values().random().name
                emails.add(Email("fake@fake.com").value)
                phones.add(Phone.fake)
            }
    }
}
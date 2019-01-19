package com.asofttz.auth

import com.asofttz.auth.neo4j.GeneratedValue
import com.asofttz.auth.neo4j.Id
import com.asofttz.auth.neo4j.NodeEntity
import com.asofttz.date.Date
import com.asofttz.date.DateFactory
import com.asofttz.date.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

@Serializable
@NodeEntity
class User {
    @Id
    @GeneratedValue
    var id: Long? = null
    var fullname = ""
    var username = ""
    var permits = mutableListOf(":settings", ":logs")
    var emails = mutableListOf<String>()
    var phones = mutableListOf<String>()
    var gender: Gender = Gender.Male
    var profilePic: String = ""
    var isBlocked = false

    @Serializable(with = DateSerializer::class)
    var registeredOn = DateFactory.today()

    @Serializable(with = DateSerializer::class)
    var lastSeen: Date = DateFactory.today()

    @Serializable(with = DateSerializer::class)
    var lastModified: Date = DateFactory.today()

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

    fun clone(): User = JSON.parse(User.serializer(), JSON.stringify(User.serializer(), this))

    companion object {
        private val fakeNames = mutableListOf("Raiden", "Anderson", "Hanzo", "Lameck", "Hasashi", "Kenshi", "Takeda", "Jackson", "Sonya", "Tremor", "Kotal", "Khan", "Cassie", "Johnny", "Cage", "Kabal", "Enenra", "Cyrax", "Sektor", "Jean", "T'Challa", "T'Chaka", "Okoye", "Wakabi")
        private val mailProviders = mutableListOf("google.com", "yahoo.com", "mail.com", "people.com", "asofttz.com")
        val fakeUser: User
            get() = User().apply {
                val fName = fakeNames.random()
                fullname = "$fName ${fakeNames.random()}"
                username = fName.toLowerCase()
                emails.add(fullname.replace(" ", "").toLowerCase() + "@" + mailProviders.random().replace(",", ""))
                phones.add("+255 752 748 674")
            }
    }
}
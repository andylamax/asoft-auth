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
open class User {
    @Id
    @GeneratedValue
    var id: Long? = null
    var name = ""
    var password = ""
    var username = ""
    var permits = arrayOf(":settings", ":logs")
    var emails = arrayOf<String>()
    var phones = arrayOf<String>()
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
        var lastId = 0L
        private val mailProviders = arrayOf("google.com", "yahoo.com", "mail.com", "people.com", "asofttz.com")
        val fakeUser: User
            get() = User().apply {
                id = ++lastId
                name = Name.fakeName
                username = name.asName().first.toLowerCase()
                password = "1234"
                val email = "${name.asName().last.replace("'", "")}.${name.asName().first.replace("'", "")}@${mailProviders.random()}".toLowerCase()
                emails += email
                phones += "255752748674"
            }
    }
}
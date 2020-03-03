package dao

import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserAccount
import tz.co.asoft.auth.dao.IUsersDao
import tz.co.asoft.email.Email
import tz.co.asoft.io.File
import tz.co.asoft.persist.dao.Cache
import tz.co.asoft.phone.Phone

class UsersDao : Cache<User>(), IUsersDao {
    override suspend fun uploadPhoto(user: User, photo: File) = user

    override suspend fun create(t: User): User {
        t.uid = all().size.toString()
        return super<Cache>.create(t)
    }

    override suspend fun load(email: Email, pwd: String) = all().firstOrNull {
        it.emails.contains(email.value) && it.password == pwd
    }

    override suspend fun load(phone: Phone, pwd: String) = all().firstOrNull {
        it.emails.contains(phone.value) && it.password == pwd
    }

    override suspend fun loadUsers(ua: UserAccount) = all().filter { u ->
        u.accounts.map { it.uid }.contains(ua.uid)
    }
}
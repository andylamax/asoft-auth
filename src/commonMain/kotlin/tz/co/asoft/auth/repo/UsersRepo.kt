package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserAccount
import tz.co.asoft.auth.dao.IUsersDao
import tz.co.asoft.auth.dao.IUsersLocalDao
import tz.co.asoft.email.Email
import tz.co.asoft.io.File
import tz.co.asoft.phone.Phone

class UsersRepo(override val remoteDao: IUsersDao, override val localDao: IUsersLocalDao) : IUsersRepo {

    override suspend fun load(email: Email, pwd: String) = remoteDao.load(email, pwd)?.also { localDao.create(it) }

    override suspend fun load(phone: Phone, pwd: String) = remoteDao.load(phone, pwd)?.also { localDao.create(it) }

    override suspend fun uploadPhoto(user: User, photo: File) = remoteDao.uploadPhoto(user, photo).also {
        localDao.delete()
        localDao.create(it)
    }

    override suspend fun loadUsers(ua: UserAccount): List<User> = all().filter { user ->
        user.accounts.map { it.uid }.contains(ua.uid)
    }

    override suspend fun loadLocalUser(): User? = localDao.load()

    override suspend fun userWithEmailExists(emails: List<String>): Boolean = all().any { user ->
        user.emails.any { emails.contains(it) }
    }

    override suspend fun userWithPhoneExists(phones: List<String>) = all().any { user ->
        user.phones.any { phones.contains(it) }
    }

    override suspend fun edit(t: User): User {
        if (localDao.load()?.uid == t.uid) {
            localDao.delete()
        }
        return super.edit(t)
    }

    override suspend fun delete(t: User): User {
        if (localDao.load()?.uid == t.uid) {
            localDao.delete()
        }
        return super.delete(t)
    }

    override suspend fun deleteLocal() = localDao.delete()
}
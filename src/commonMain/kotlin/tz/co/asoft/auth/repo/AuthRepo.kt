package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.IAuthDao
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.Phone
import tz.co.asoft.auth.dao.IAuthLocalDao
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.repo.Repo

class AuthRepo(private val dao: IAuthDao, private val localDao: IAuthLocalDao?) : Repo<User>(dao), IAuthRepo {

    override suspend fun load(email: Email, pwd: String) = dao.load(email, pwd)?.also { it.saveToLocalDb() }

    override suspend fun load(phone: Phone, pwd: String) = dao.load(phone, pwd)?.also { it.saveToLocalDb() }

    private suspend fun User.saveToLocalDb() = localDao?.create(this)

    override suspend fun uploadPhoto(user: User, photo: File) = dao.uploadPhoto(user, photo)?.also {
        localDao?.delete()
        localDao?.create(it)
    }

    override suspend fun saveToLocal(u: User) = u.saveToLocalDb()

    override suspend fun loadLocalUser() = localDao?.load()

    override suspend fun deleteLocal() {
        localDao?.delete()
    }

    override suspend fun userWithEmailExists(emails: List<String>): Boolean = all()?.any { user ->
        user.emails.any { emails.contains(it) }
    } ?: true

    override suspend fun userWithPhoneExists(phones: List<String>) = all()?.any { user ->
        user.phones.any { phones.contains(it) }
    } ?: true

    override suspend fun edit(t: User): User? {
        if (localDao?.load()?.uid == t.uid) {
            localDao.delete()
            localDao.create(t)
        }
        return super<Repo>.edit(t)
    }

    override suspend fun delete(t: User): User? {
        if (localDao?.load()?.uid == t.uid) {
            localDao.delete()
        }
        return dao.delete(t)
    }
}
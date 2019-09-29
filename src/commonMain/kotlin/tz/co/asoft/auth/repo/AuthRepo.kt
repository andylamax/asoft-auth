package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.IAuthDao
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.Phone
import tz.co.asoft.auth.dao.IAuthLocalDao
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.repo.Repo

class AuthRepo(private val dao: Dao<User>, private val localDao: Dao<User>?) : Repo<User>(dao), IAuthRepo {

    override suspend fun load(email: Email, pwd: String) = if (dao is IAuthDao) {
        dao.load(email, pwd)?.also { it.saveToLocalDb() }
    } else null

    override suspend fun load(phone: Phone, pwd: String) = if (dao is IAuthDao) {
        dao.load(phone, pwd)?.also { it.saveToLocalDb() }
    } else null

    private suspend fun User.saveToLocalDb() = localDao?.create(this)

    override suspend fun uploadPhoto(user: User, photo: File) = if (dao is IAuthDao) {
        dao.uploadPhoto(user, photo)?.also {
            if (localDao is IAuthLocalDao?) localDao?.delete()
            localDao?.create(it)
        }
    } else null

    override suspend fun saveToLocal(u: User) = u.saveToLocalDb()
    override suspend fun loadLocalUser() = if (localDao is IAuthLocalDao?) {
        localDao?.load()
    } else null

    override suspend fun deleteLocal() {
        if (localDao is IAuthLocalDao?) localDao?.delete()
    }

    override suspend fun userWithEmailExists(emails: List<String>): Boolean = all()?.any { user ->
        user.emails.any { emails.contains(it) }
    } ?: true

    override suspend fun userWithPhoneExists(phones: List<String>) = all()?.any { user ->
        user.phones.any { phones.contains(it) }
    } ?: true

    override suspend fun edit(t: User): User? {
        if (localDao is IAuthLocalDao?) {
            if (localDao?.load()?.uid == t.uid) {
                localDao?.delete()
                localDao?.create(t)
            }
        }
        return super.edit(t)
    }

    override suspend fun delete(t: User): User? {
        if (localDao is IAuthLocalDao?) {
            if (localDao?.load()?.uid == t.uid) {
                localDao?.delete()
            }
        }
        return dao.delete(t)
    }
}
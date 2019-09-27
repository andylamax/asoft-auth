package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.AuthAbstractLocalDao
import tz.co.asoft.auth.dao.IAuthDao
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.repo.Repo

class AuthRepo(private val dao: Dao<User>, private val localDao: AuthAbstractLocalDao?) : Repo<User>(dao), IAuthRepo {

    override suspend fun emailSignIn(email: String, pwd: String) = if (dao is IAuthDao) {
        dao.emailSignIn(email, pwd)?.also {
            it.saveToLocalDb()
        }
    } else null

    override suspend fun phoneSignIn(phone: String, pwd: String) = if (dao is IAuthDao) {
        dao.phoneSignIn(phone, pwd)?.also {
            it.saveToLocalDb()
        }
    } else null

    private suspend fun User.saveToLocalDb() = localDao?.save(this)

    override suspend fun uploadPhoto(user: User, photo: File) = if (dao is IAuthDao) {
        dao.uploadPhoto(user, photo)?.also {
            localDao?.delete()
            localDao?.save(it)
        }
    } else null

    override suspend fun saveToLocal(u: User) = u.saveToLocalDb()
    override suspend fun loadLocalUser() = localDao?.load()

    override suspend fun userWithEmailExists(emails: List<String>): Boolean = all()?.any { user ->
        user.emails.any { emails.contains(it) }
    } ?: true

    override suspend fun userWithPhoneExists(phones: List<String>) = all()?.any { user ->
        user.phones.any { phones.contains(it) }
    } ?: true

    override suspend fun edit(t: User): User? {
        if (localDao?.load()?.uid == t.uid) {
            localDao.save(t)
        }
        return super.edit(t)
    }

    override suspend fun delete(t: User): User? {
        if (localDao?.load()?.uid == t.uid) {
            localDao.delete()
        }
        return dao.delete(t)
    }
}
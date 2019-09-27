package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.AuthAbstractDao
import tz.co.asoft.auth.dao.AuthAbstractLocalDao
import tz.co.asoft.auth.exceptions.Exceptions
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.tools.Cause

abstract class AuthAbstractRepo(
        private val dao: AuthAbstractDao,
        private val localDao: AuthAbstractLocalDao?
) : Repo<User>(dao) {

    open suspend fun emailSignIn(email: String, pwd: String) = dao.emailSignIn(email, pwd)?.also {
        it.saveToLocalDb()
    }

    open suspend fun phoneSignIn(phone: String, pwd: String) = dao.phoneSignIn(phone, pwd)?.also {
        it.saveToLocalDb()
    }

    private suspend fun User.saveToLocalDb() = localDao?.save(this)

    open suspend fun uploadPhoto(user: User, photo: File) = dao.uploadPhoto(user, photo)?.also {
        localDao?.delete()
        localDao?.save(it)
    }

    open suspend fun loadLocalUser() = localDao?.load()

    open suspend fun userWithEmailExists(emails: List<String>): Boolean = all()?.any { user ->
        user.emails.any { emails.contains(it) }
    } ?: true

    open suspend fun userWithPhoneExists(phones: List<String>) = all()?.any { user ->
        user.phones.any { phones.contains(it) }
    } ?: true

    override suspend fun edit(t: User): User? {
        localDao?.save(t)
        return super.edit(t)
    }

    override suspend fun delete(t: User): User? {
        localDao?.delete()
        return dao.delete(t)
    }
}
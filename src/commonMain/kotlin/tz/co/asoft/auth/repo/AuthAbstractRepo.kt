package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.rx.observers.Observable
import tz.co.asoft.rx.subscriber.Subscriber
import tz.co.asoft.auth.dao.AuthAbstractDao
import tz.co.asoft.auth.dao.AuthAbstractLocalDao
import tz.co.asoft.persist.tools.Cause
import tz.co.asoft.auth.exceptions.Exceptions

abstract class AuthAbstractRepo(
        private val dao: AuthAbstractDao,
        private val localDao: AuthAbstractLocalDao?
) : PaginatedRepo<User>(dao) {

    var currentUser = Observable<User?>(null)

    open suspend fun emailSignIn(email: String, pwd: String) = dao.emailSignIn(email, pwd).also {
        onSignIn(it)
    }

    open suspend fun phoneSignIn(phone: String, pwd: String) = dao.phoneSignIn(phone, pwd).also {
        onSignIn(it)
    }

    open suspend fun uploadPhoto(user: User, photoRef: Any): User? {
        val user = dao.uploadPhoto(user, photoRef) ?: return null
        currentUser.value = user
        localDao?.apply {
            signOut()
            save(user)
        }
        return user
    }

    open suspend fun onSignIn(user: User?) {
        currentUser.value = user
        if (user != null) {
            localDao?.save(user)
        }
    }

    open suspend fun userWithEmailExists(emails: List<String>) = all().any { user ->
        user.emails.any { emails.contains(it) }
    }

    open suspend fun userWithPhoneExists(phones: List<String>) = all().any { user ->
        user.phones.any { phones.contains(it) }
    }

    override suspend fun create(t: User) = when {
        userWithEmailExists(t.emails) -> throw Cause(Exceptions.UserWithSameEmailExists.name)
        userWithPhoneExists(t.phones) -> throw  Cause(Exceptions.UserWithSamePhoneExists.name)
        else -> super.create(t)
    }

    open suspend fun signOut(): User? = currentUser.value?.let {
        dao.signOut(it)
        currentUser.value = null
        localDao?.signOut()
        it
    }

    open suspend fun onAuthStateChanged(handler: (User?) -> Unit): Subscriber<User?> {
        if (currentUser.value == null && localDao != null) {
            currentUser.value = localDao.load()
        }
        return currentUser.subscribe(handler)
    }
}
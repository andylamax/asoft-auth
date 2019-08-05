package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.rx.observers.Observable
import tz.co.asoft.rx.subscriber.Subscriber
import tz.co.asoft.auth.dao.AuthAbstractDao
import tz.co.asoft.auth.dao.AuthAbstractLocalDao

abstract class AuthAbstractRepo(
        private val dao: AuthAbstractDao,
        private val localDao: AuthAbstractLocalDao?
) : PaginatedRepo<User>(dao) {

    private var currentUser = Observable<User?>(null)

    suspend fun emailSignIn(email: String, pwd: String) = dao.emailSignIn(email, pwd).also {
        onSignIn(it)
    }

    suspend fun phoneSignIn(phone: String, pwd: String) = dao.phoneSignIn(phone, pwd).also {
        onSignIn(it)
    }

    suspend fun uploadPhoto(user: User, photoRef: Any) = dao.uploadPhoto(user, photoRef)

    private suspend fun onSignIn(user: User?) {
        currentUser.value = user
        if (user != null) {
            localDao?.save(user)
        }
    }

    suspend fun signOut() = dao.signOut().also {
        currentUser.value = null
        localDao?.signOut()
    }

    suspend fun onAuthStateChanged(handler: (User?) -> Unit): Subscriber<User?> {
        if (currentUser.value == null && localDao != null) {
            currentUser.value = localDao.load()
        }
        return currentUser.subscribe(handler)
    }
}
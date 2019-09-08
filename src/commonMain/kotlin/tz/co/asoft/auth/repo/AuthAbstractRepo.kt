package tz.co.asoft.auth.repo

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.rx.observers.Observable
import tz.co.asoft.rx.subscriber.Subscriber
import tz.co.asoft.auth.dao.AuthAbstractDao
import tz.co.asoft.auth.dao.AuthAbstractLocalDao
import tz.co.asoft.persist.tools.Cause
import tz.co.asoft.auth.exceptions.Exceptions
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

abstract class AuthAbstractRepo(
        private val dao: AuthAbstractDao,
        private val localDao: AuthAbstractLocalDao?
) : PaginatedRepo<User>(dao) {

    open val loggedUser = LiveData<User?>(null)
    open var currentUser = Observable<User?>(null)

    open suspend fun emailSignIn(email: String, pwd: String) = dao.emailSignIn(email, pwd).also {
        onSignIn(it)
    }

    open suspend fun emailSignInCatching(email: String, pwd: String) = Result.catching { emailSignIn(email, pwd) }

    open suspend fun phoneSignIn(phone: String, pwd: String) = dao.phoneSignIn(phone, pwd).also {
        onSignIn(it)
    }

    open suspend fun phoneSignInCatching(phone: String, pwd: String) = Result.catching { phoneSignIn(phone, pwd) }

    open suspend fun uploadPhoto(user: User, photoRef: Any): User? {
        val user = dao.uploadPhoto(user, photoRef) ?: return null
        currentUser.value = user
        localDao?.apply {
            signOut()
            save(user)
        }
        return user
    }

    open suspend fun uploadPhotoCatching(user: User, photoRef: Any) = Result.catching { uploadPhoto(user, photoRef) }

    open suspend fun onSignIn(user: User?) {
        currentUser.value = user
        loggedUser.value = user
        if (user != null) {
            localDao?.save(user)
        }
    }

    open suspend fun userWithEmailExists(emails: List<String>): Boolean = all()?.any { user ->
        user.emails.any { emails.contains(it) }
    } ?: true

    open suspend fun userWithPhoneExists(phones: List<String>) = all()?.any { user ->
        user.phones.any { phones.contains(it) }
    } ?: true

    override suspend fun create(t: User) = when {
        userWithEmailExists(t.emails) -> throw Cause(Exceptions.UserWithSameEmailExists.name)
        userWithPhoneExists(t.phones) -> throw  Cause(Exceptions.UserWithSamePhoneExists.name)
        else -> super.create(t)
    }

    open suspend fun signOut(): User? = currentUser.value?.let {
        dao.signOut(it)
        currentUser.value = null
        loggedUser.value = null
        localDao?.signOut()
        it
    }

    open suspend fun onAuthStateChanged(handler: (User?) -> Unit): Subscriber<User?> = coroutineScope {
        if (currentUser.value == null && localDao != null)
            launch { currentUser.value = localDao.load() }
        currentUser.subscribe(handler)
    }

    open suspend fun observeLoggedUser(lifeCycle: LifeCycle, handler: (User?) -> Unit) = coroutineScope {
        if (loggedUser.value == null && localDao != null)
            launch { loggedUser.value = localDao.load() }
        loggedUser.observe(lifeCycle, handler)
    }
}
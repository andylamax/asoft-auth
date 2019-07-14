package tz.co.asoft.auth.data.viewmodel

import tz.co.asoft.auth.User
import tz.co.asoft.persist.tools.Lockable
import tz.co.asoft.persist.viewmodel.ViewModel
import tz.co.asoft.auth.data.repo.AuthAbstractRepo

open class AuthViewModel(private val repo: AuthAbstractRepo) : ViewModel<User>(repo),
    Lockable {
    final override var isRunning = false

    enum class SignInType {
        email, phone
    }

    suspend fun signIn(loginId: String, pwd: String): User? {
        val type = if (loginId.contains("@")) {
            SignInType.email
        } else {
            SignInType.phone
        }
        return when (type) {
            SignInType.email -> emailSignIn(loginId, pwd)
            SignInType.phone -> phoneSignIn(loginId, pwd)
        }
    }

    suspend fun emailSignIn(email: String, pwd: String) = repo.emailSignIn(email, pwd)
    suspend fun phoneSignIn(phone: String, pwd: String) = repo.phoneSignIn(phone, pwd)
    suspend fun signOut() = repo.signOut()
    suspend fun onAuthStateChanged(handler: (User?) -> Unit) = repo.onAuthStateChanged(handler)
}
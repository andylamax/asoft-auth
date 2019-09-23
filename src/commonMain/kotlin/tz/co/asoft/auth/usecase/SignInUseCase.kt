package tz.co.asoft.auth.usecase

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.AuthAbstractRepo
import tz.co.asoft.auth.viewmodel.AuthViewModel

class SignInUseCase(private val repo: AuthAbstractRepo) {
    suspend fun signIn(loginId: String, pwd: String): User? {
        val type = if (loginId.contains("@")) {
            AuthViewModel.SignInType.email
        } else {
            AuthViewModel.SignInType.phone
        }
        return when (type) {
            AuthViewModel.SignInType.email -> repo.emailSignIn(loginId, pwd)
            AuthViewModel.SignInType.phone -> repo.phoneSignIn(loginId, pwd)
        }
    }
}
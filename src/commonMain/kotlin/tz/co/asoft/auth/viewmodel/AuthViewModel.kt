package tz.co.asoft.auth.viewmodel

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.AuthAbstractRepo
import tz.co.asoft.auth.usecase.SignInUseCase
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.viewmodel.PaginatedViewModel

open class AuthViewModel(
        private val repo: AuthAbstractRepo,
        private val signInUseCase: SignInUseCase = SignInUseCase(repo)
) : PaginatedViewModel<User>(repo) {

    enum class SignInType {
        email, phone
    }

    suspend fun signIn(loginId: String, pwd: String) = signInUseCase.signIn(loginId, pwd)

    suspend fun uploadPhoto(user: User, photo: File) = repo.uploadPhoto(user, photo)
    suspend fun emailSignIn(email: String, pwd: String) = repo.emailSignIn(email, pwd)
    suspend fun phoneSignIn(phone: String, pwd: String) = repo.phoneSignIn(phone, pwd)
    suspend fun signOut() = repo.signOut()
    suspend fun onAuthStateChanged(handler: (User?) -> Unit) = repo.onAuthStateChanged(handler)
}
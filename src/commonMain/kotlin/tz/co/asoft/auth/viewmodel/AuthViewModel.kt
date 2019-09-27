package tz.co.asoft.auth.viewmodel

import tz.co.asoft.auth.User
import tz.co.asoft.auth.usecase.*
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.viewmodel.ViewModel
import tz.co.asoft.rx.lifecycle.LifeCycle

open class AuthViewModel(
        repo: Repo<User>,
        private val createAdminUC: CreateAdminUseCase,
        private val registerUserUC: IRegisterUserUseCase,
        private val signInUC: SignInUseCase,
        private val authStateUC: AuthStateUseCase,
        private val signOutUC: SignOutUseCase,
        private val uploadPhotoUC: UploadPhotoUseCase
) : ViewModel<User>(repo) {
    suspend fun createAdmin() = createAdminUC()
    suspend fun signIn(loginId: String, pwd: String) = signInUC(loginId, pwd)
    suspend fun uploadPhoto(user: User, photo: File) = uploadPhotoUC(user, photo)
    suspend fun signOut() = signOutUC()
    suspend fun onAuthStateChanged(lifeCycle: LifeCycle, handler: (User?) -> Unit) = authStateUC.onAuthStateChanged(lifeCycle, handler)
    suspend fun createUser(user: User) = registerUserUC(user)
}
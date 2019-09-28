package tz.co.asoft.auth.viewmodel

import tz.co.asoft.auth.User
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.auth.usecase.createadmin.ICreateAdminUseCase
import tz.co.asoft.auth.usecase.registeruser.IRegisterUserUseCase
import tz.co.asoft.auth.usecase.signin.ISignInUseCase
import tz.co.asoft.auth.usecase.signout.ISignOutUseCase
import tz.co.asoft.auth.usecase.uploadphoto.IUploadPhotoUseCase
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.viewmodel.ViewModel
import tz.co.asoft.rx.lifecycle.LifeCycle

open class AuthViewModel(
        repo: Repo<User>,
        private val createAdminUC: ICreateAdminUseCase,
        private val registerUserUC: IRegisterUserUseCase,
        private val signInUC: ISignInUseCase,
        private val authStateUC: IAuthStateUseCase,
        private val signOutUC: ISignOutUseCase,
        private val uploadPhotoUC: IUploadPhotoUseCase
) : ViewModel<User>(repo) {
    suspend fun createAdmin() = createAdminUC()
    suspend fun signIn(loginId: String, pwd: String) = signInUC(loginId, pwd)
    suspend fun uploadPhoto(user: User, photo: File) = uploadPhotoUC(user, photo)
    fun signOut() = signOutUC()
    suspend fun onAuthStateChanged(lifeCycle: LifeCycle, handler: (User?) -> Unit) = authStateUC.onAuthStateChanged(lifeCycle, handler)
    suspend fun registerUser(user: User) = registerUserUC(user)
}
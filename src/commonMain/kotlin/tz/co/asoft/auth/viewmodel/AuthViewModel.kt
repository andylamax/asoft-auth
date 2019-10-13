package tz.co.asoft.auth.viewmodel

import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserRef
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.usecase.authstate.AuthStateUseCase
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.auth.usecase.createadmin.CreateAdminUseCase
import tz.co.asoft.auth.usecase.createadmin.ICreateAdminUseCase
import tz.co.asoft.auth.usecase.deleteuser.DeleteUserUseCase
import tz.co.asoft.auth.usecase.deleteuser.IDeleteUserUseCase
import tz.co.asoft.auth.usecase.loaduser.ILoadUserUseCase
import tz.co.asoft.auth.usecase.loaduser.LoadUserUseCase
import tz.co.asoft.auth.usecase.registeruser.IRegisterUserUseCase
import tz.co.asoft.auth.usecase.registeruser.RegisterUserUseCase
import tz.co.asoft.auth.usecase.registeruserandsignin.IRegisterUserAndSignInUseCase
import tz.co.asoft.auth.usecase.registeruserandsignin.RegisterUserAndSignInUseCase
import tz.co.asoft.auth.usecase.signin.ISignInUseCase
import tz.co.asoft.auth.usecase.signin.SignInUseCase
import tz.co.asoft.auth.usecase.signout.ISignOutUseCase
import tz.co.asoft.auth.usecase.signout.SignOutUseCase
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.auth.usecase.updatestatus.UpdateStatusUseCase
import tz.co.asoft.auth.usecase.uploadphoto.IUploadPhotoUseCase
import tz.co.asoft.auth.usecase.uploadphoto.UploadPhotoUseCase
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.viewmodel.ViewModel
import tz.co.asoft.rx.lifecycle.ILifeCycle
import tz.co.asoft.rx.lifecycle.LifeCycle

open class AuthViewModel(
        repo: IAuthRepo,
        private val authStateUC: IAuthStateUseCase = AuthStateUseCase.getInstance(repo),
        private val registerUserUC: IRegisterUserUseCase = RegisterUserUseCase(repo),
        private val loadUserUC: ILoadUserUseCase = LoadUserUseCase(repo),
        private val updateStatusUC: IUpdateStatusUseCase = UpdateStatusUseCase(repo),
        private val createAdminUC: ICreateAdminUseCase = CreateAdminUseCase(registerUserUC),
        private val signInUC: ISignInUseCase = SignInUseCase(loadUserUC, authStateUC, updateStatusUC),
        private val registerUserAndSignInUC: IRegisterUserAndSignInUseCase = RegisterUserAndSignInUseCase(registerUserUC, signInUC),
        private val signOutUC: ISignOutUseCase = SignOutUseCase(repo, authStateUC, updateStatusUC),
        private val uploadPhotoUC: IUploadPhotoUseCase = UploadPhotoUseCase(repo, authStateUC),
        private val deleteUserUC: IDeleteUserUseCase = DeleteUserUseCase(repo, loadUserUC)
) : ViewModel<User>(repo) {
    suspend fun createAdmin() = createAdminUC()
    suspend fun signIn(loginId: String, pwd: String) = signInUC(loginId, pwd)
    suspend fun uploadPhoto(user: User, photo: File) = uploadPhotoUC(user, photo)
    fun signOut() = signOutUC()
    suspend fun onAuthStateChanged(lifeCycle: ILifeCycle, handler: (User?) -> Unit) = authStateUC.onAuthStateChanged(lifeCycle, handler)
    suspend fun registerUser(user: User) = registerUserUC(user)
    suspend fun registerUserAndSignIn(user: User) = registerUserAndSignInUC(user)
    suspend fun deleteUser(email: String, pwd: String) = deleteUserUC(email, pwd)
    suspend fun loadUser(email: String, pwd: String) = loadUserUC(email, pwd)
    suspend fun loadUser(ref: UserRef) = loadUserUC(ref)
}
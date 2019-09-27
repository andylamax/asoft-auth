package tz.co.asoft.auth.viewmodel

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.AuthAbstractRepo
import tz.co.asoft.auth.usecase.SigningUseCase
import tz.co.asoft.auth.usecase.UploadPhotoUseCase
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.viewmodel.ViewModel
import tz.co.asoft.rx.lifecycle.LifeCycle

open class AuthViewModel(
        private val repo: AuthAbstractRepo,
        private val signingUC: SigningUseCase,
        private val uploadPhotoUC: UploadPhotoUseCase
) : ViewModel<User>(repo) {
    suspend fun signIn(loginId: String, pwd: String) = signingUC.signIn(loginId, pwd)
    suspend fun uploadPhoto(user: User, photo: File) = uploadPhotoUC(user, photo)
    suspend fun signOut() = signingUC.signOut()
    suspend fun onAuthStateChanged(lifeCycle: LifeCycle, handler: (User?) -> Unit) = signingUC.onAuthStateChanged(lifeCycle, handler)
}
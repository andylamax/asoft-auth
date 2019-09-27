package tz.co.asoft.auth.usecase

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.AuthAbstractRepo
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.tools.Cause

class UploadPhotoUseCase(val repo: AuthAbstractRepo, private val signingUC: SigningUseCase) {
    suspend operator fun invoke(user: User, file: File): User? {
        val res = repo.uploadPhoto(user, file) ?: throw Cause("Failed To Upload Photo")
        signingUC.liveUser.value = res
        return res
    }
}
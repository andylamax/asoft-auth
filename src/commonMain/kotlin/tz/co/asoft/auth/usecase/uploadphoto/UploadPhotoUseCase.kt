package tz.co.asoft.auth.usecase.uploadphoto

import kotlinx.coroutines.coroutineScope
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

open class UploadPhotoUseCase(
        private val repo: IAuthRepo,
        private val authStateUC: IAuthStateUseCase
) : IUploadPhotoUseCase {

    override suspend operator fun invoke(u: User, file: File) = coroutineScope {
        Result.catching {
            val user = repo.uploadPhoto(u, file) ?: throw Cause("Failed To Upload Photo")
            authStateUC.liveUser.value = user
            repo.saveToLocal(user)
            repo.edit(user)
        }
    }
}
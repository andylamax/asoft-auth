package tz.co.asoft.auth.usecase.uploadphoto

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.usecase.authstate.AuthStateUseCase
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.auth.usecase.uploadphoto.IUploadPhotoUseCase
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

open class UploadPhotoUseCase(
        private val repo: Repo<User>,
        private val authStateUC: IAuthStateUseCase
) : IUploadPhotoUseCase {

    override suspend operator fun invoke(u: User, file: File) = coroutineScope {
        Result.catching {
            if (repo is IAuthRepo) {
                val user = repo.uploadPhoto(u, file) ?: throw Cause("Failed To Upload Photo")
                authStateUC.liveUser.value = user
                repo.saveToLocal(user)
                repo.edit(user)
            } else {
                null
            }
        }
    }
}
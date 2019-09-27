package tz.co.asoft.auth.usecase

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

open class UploadPhotoUseCase(private val repo: Repo<User>, private val authStateUC: AuthStateUseCase) {

    suspend operator fun invoke(u: User, file: File) = coroutineScope {
        Result.catching {
            if (repo is IAuthRepo) {
                val user = repo.uploadPhoto(u, file) ?: throw Cause("Failed To Upload Photo")
                authStateUC.liveUser.value = user
                launch {
                    repo.saveToLocal(user)
                    repo.edit(user)
                }
                user
            } else {
                null
            }
        }
    }
}
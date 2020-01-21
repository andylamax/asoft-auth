package tz.co.asoft.auth.usecase.uploadphoto

import kotlinx.coroutines.coroutineScope
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.usecase.userstate.IUserStateUseCase
import tz.co.asoft.io.File
import tz.co.asoft.persist.result.catching

open class UploadPhotoUseCase(
        private val repo: IUsersRepo,
        private val userStateUC: IUserStateUseCase
) : IUploadPhotoUseCase {

    override suspend operator fun invoke(u: User, file: File) = coroutineScope {
        catching {
            val user = repo.uploadPhoto(u, file)
            userStateUC.liveUser.value = user
            repo.edit(user)
        }
    }
}
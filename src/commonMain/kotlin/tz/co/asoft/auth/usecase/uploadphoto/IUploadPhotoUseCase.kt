package tz.co.asoft.auth.usecase.uploadphoto

import tz.co.asoft.auth.User
import tz.co.asoft.io.File
import tz.co.asoft.persist.result.Result

interface IUploadPhotoUseCase {
    suspend operator fun invoke(u: User, file: File): Result<User>
}
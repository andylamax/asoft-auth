package tz.co.asoft.auth.usecase.loaduser

import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserRef
import tz.co.asoft.persist.result.Result

interface ILoadUserUseCase {
    suspend operator fun invoke(loginId: String, pwd: String): Result<User>
    suspend operator fun invoke(uid: Any): Result<User>
    suspend operator fun invoke(ref: UserRef): Result<User>
}
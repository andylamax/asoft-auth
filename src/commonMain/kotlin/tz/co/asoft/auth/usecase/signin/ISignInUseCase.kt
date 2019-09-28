package tz.co.asoft.auth.usecase.signin

import tz.co.asoft.auth.User
import tz.co.asoft.persist.result.Result

interface ISignInUseCase {
    suspend operator fun invoke(loginId: String, pwd: String): Result<User>
}
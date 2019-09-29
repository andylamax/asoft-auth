package tz.co.asoft.auth.usecase.deleteuser

import tz.co.asoft.auth.User
import tz.co.asoft.persist.result.Result

interface IDeleteUserUseCase {
    suspend operator fun invoke(email: String, pwd: String): Result<User>
}
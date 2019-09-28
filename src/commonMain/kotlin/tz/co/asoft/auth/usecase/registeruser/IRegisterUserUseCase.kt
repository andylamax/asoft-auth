package tz.co.asoft.auth.usecase.registeruser

import tz.co.asoft.auth.User
import tz.co.asoft.persist.result.Result

interface IRegisterUserUseCase {
    suspend operator fun invoke(user: User): Result<User>
}
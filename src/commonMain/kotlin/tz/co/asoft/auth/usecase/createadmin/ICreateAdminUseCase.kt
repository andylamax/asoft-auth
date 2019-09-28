package tz.co.asoft.auth.usecase.createadmin

import tz.co.asoft.auth.User
import tz.co.asoft.persist.result.Result

interface ICreateAdminUseCase {
    suspend operator fun invoke() : Result<User>
}
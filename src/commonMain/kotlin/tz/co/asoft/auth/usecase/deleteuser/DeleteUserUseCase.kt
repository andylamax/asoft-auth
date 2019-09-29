package tz.co.asoft.auth.usecase.deleteuser

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.usecase.loaduser.ILoadUserUseCase
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

class DeleteUserUseCase(
        private val repo: Repo<User>,
        private val loadUserUC: ILoadUserUseCase
) : IDeleteUserUseCase {

    override suspend fun invoke(email: String, pwd: String) = Result.catching {
        val user = loadUserUC(email, pwd).respond()
        repo.delete(user)
    }
}
package tz.co.asoft.auth.usecase.deleteuser

import tz.co.asoft.auth.User
import tz.co.asoft.auth.usecase.loaduser.ILoadUserUseCase
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.result.Result

class DeleteUserUseCase(
        private val repo: IRepo<User>,
        private val loadUserUC: ILoadUserUseCase
) : IDeleteUserUseCase {

    override suspend fun invoke(email: String, pwd: String) = Result.catching {
        val user = loadUserUC(email, pwd).respond()
        repo.delete(user)
    }
}
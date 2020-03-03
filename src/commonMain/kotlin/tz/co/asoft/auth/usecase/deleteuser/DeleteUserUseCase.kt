package tz.co.asoft.auth.usecase.deleteuser

import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.email.Email
import tz.co.asoft.persist.result.catching
import tz.co.asoft.persist.tools.Cause

class DeleteUserUseCase(
        private val repo: IUsersRepo
) : IDeleteUserUseCase {

    override suspend fun invoke(email: String, pwd: String) = catching {
        val user = repo.load(Email(email), pwd) ?: throw Cause("User not found")
        repo.delete(user)
    }
}
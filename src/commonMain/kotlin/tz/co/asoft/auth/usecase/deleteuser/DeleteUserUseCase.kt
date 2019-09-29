package tz.co.asoft.auth.usecase.deleteuser

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.Email
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

class DeleteUserUseCase(private val repo: Repo<User>) : IDeleteUserUseCase {

    override suspend fun invoke(email: String, pwd: String): Result<User> = try {
        if (repo is IAuthRepo) {
            Result.catching {
                val user = repo.load(Email(email), pwd) ?: throw Cause("User with email $email not found")
                repo.delete(user)
            }
        } else {
            throw Cause("Can't delete user from a non AuthRepo")
        }
    } catch (c: Cause) {
        Result.failure(c)
    }
}
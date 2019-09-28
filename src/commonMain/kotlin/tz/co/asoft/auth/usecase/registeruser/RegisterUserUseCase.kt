package tz.co.asoft.auth.usecase.registeruser

import com.soywiz.krypto.SHA256
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.auth.usecase.signin.ISignInUseCase
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

open class RegisterUserUseCase(
        private val repo: Repo<User>,
        private val signInUC: ISignInUseCase
) : IRegisterUserUseCase {
    override suspend operator fun invoke(u: User): Result<User> {
        val pwd = u.password
        return try {
            u.password = SHA256.digest(u.password.toUtf8Bytes()).hex
            if (repo is IAuthRepo) {
                if (repo.userWithEmailExists(u.emails)) throw emailExists()
                if (repo.userWithPhoneExists(u.emails)) throw phoneExists()
            }
            repo.create(u) ?: throw cause(u)
            signInUC(u.emails.first(), pwd)
        } catch (c: Cause) {
            Result.failure(c)
        }
    }

    private fun cause(u: User) = Cause("Failed to store ${u.name}'s info")

    private fun emailExists() = Cause("User with same email already exists")

    private fun phoneExists() = Cause("User with same phone already exists")
}
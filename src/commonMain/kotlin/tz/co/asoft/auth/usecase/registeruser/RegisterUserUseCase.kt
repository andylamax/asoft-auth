package tz.co.asoft.auth.usecase.registeruser

import com.soywiz.krypto.SHA256
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

open class RegisterUserUseCase(private val repo: IAuthRepo) : IRegisterUserUseCase {

    override suspend operator fun invoke(user: User) = Result.catching {
        user.password = SHA256.digest(user.password.toUtf8Bytes()).hex
        if (repo.userWithEmailExists(user.emails)) throw emailExists()
        if (repo.userWithPhoneExists(user.phones)) throw phoneExists()
        repo.create(user)
    }

    private fun emailExists() = Cause("User with same email already exists")

    private fun phoneExists() = Cause("User with same phone already exists")
}
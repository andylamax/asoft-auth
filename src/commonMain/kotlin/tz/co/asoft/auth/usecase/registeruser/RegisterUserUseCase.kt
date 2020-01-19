package tz.co.asoft.auth.usecase.registeruser

import com.soywiz.krypto.SHA256
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserAccount
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.result.catching
import tz.co.asoft.persist.tools.Cause

open class RegisterUserUseCase(private val usersRepo: IUsersRepo, private val accountsRepo: IRepo<UserAccount>) : IRegisterUserUseCase {

    override suspend operator fun invoke(user: User, ua: UserAccount?) = catching {
        val account = ua ?: UserAccount().apply {
            name = user.name
            permits = user.permits
        }
        val userAccount = accountsRepo.create(account)
        user.accounts.add(userAccount)
        user.password = SHA256.digest((user.password ?: "123456").toUtf8Bytes()).hex
        if (usersRepo.userWithEmailExists(user.emails)) throw emailExists()
        if (usersRepo.userWithPhoneExists(user.phones)) throw phoneExists()
        usersRepo.create(user)
    }

    private fun emailExists() = Cause("User with same email already exists")

    private fun phoneExists() = Cause("User with same phone already exists")
}
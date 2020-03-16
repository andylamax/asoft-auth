package tz.co.asoft.auth.usecase.registeruser

import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserAccount
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.auth.tools.name.asName
import tz.co.asoft.email.Email
import tz.co.asoft.krypto.SHA256
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.result.catching
import tz.co.asoft.persist.tools.Cause
import tz.co.asoft.phone.Phone

open class RegisterUserUseCase(private val usersRepo: IUsersRepo, private val accountsRepo: IRepo<UserAccount>) : IRegisterUserUseCase {

    fun User.validated() = User().also {
        it.name = name.asName().formated()
        it.username = username
        emails.forEach { email ->
            it.emails.add(Email(email).value)
        }
        phones.forEach { phone ->
            it.phones.add(Phone(phone).value)
        }
        it.password = password ?: "123456"
        it.permissions = permissions
        it.permits = permits
        it.photoUrl = photoUrl
        it.registeredOn = registeredOn
        it.lastSeen = it.lastSeen
        it.scopes = scopes
        it.accounts = accounts
        it.verifiedEmails = verifiedEmails
        it.verifiedPhones = verifiedPhones
    }

    @OptIn(ExperimentalStdlibApi::class)
    override suspend operator fun invoke(user: User, ua: UserAccount?) = catching {
        val u = user.validated()
        val account = ua ?: UserAccount().apply {
            name = u.name
            permits = u.permits
        }
        val uAccount = accountsRepo.create(account)
        u.accounts.add(uAccount)
        u.password = SHA256.digest((u.password ?: "123456").encodeToByteArray()).hex
        if (usersRepo.userWithEmailExists(u.emails)) throw emailExists()
        if (usersRepo.userWithPhoneExists(u.phones)) throw phoneExists()
        usersRepo.create(u)
    }

    private fun emailExists() = Cause("User with same email already exists")

    private fun phoneExists() = Cause("User with same phone already exists")
}
package tz.co.asoft.auth.usecase.signin

import com.soywiz.krypto.SHA256
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.Phone
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.auth.usecase.userstate.IUserStateUseCase
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.result.catching

open class SignInUseCase(
        private val repo: IUsersRepo,
        private val userState: IUserStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignInUseCase {
    override suspend fun invoke(loginId: String, pwd: String) = catching {
        val xpwd = SHA256.digest(pwd.toUtf8Bytes()).hex
        println("Logging in now")
        if (loginId.contains("@")) {
            repo.load(Email(loginId), xpwd)
        } else {
            repo.load(Phone(loginId), xpwd)
        }?.also {
            userState.liveUser.send(it)
            updateStatusUC(it, User.Status.SignedIn)
            println("Finished logging in")
        }
    }
}
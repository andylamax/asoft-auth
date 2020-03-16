package tz.co.asoft.auth.usecase.signin

import tz.co.asoft.krypto.SHA256
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.auth.usecase.userstate.IUserStateUseCase
import tz.co.asoft.email.Email
import tz.co.asoft.persist.result.catching
import tz.co.asoft.phone.Phone

open class SignInUseCase(
        private val repo: IUsersRepo,
        private val userState: IUserStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignInUseCase {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun invoke(loginId: String, pwd: String) = catching {
        val xpwd = SHA256.digest(pwd.encodeToByteArray()).hex
        if (loginId.contains("@")) {
            repo.load(Email(loginId), xpwd)
        } else {
            repo.load(Phone(loginId), xpwd)
        }?.also {
            userState.liveUser.value = it
            updateStatusUC(it, User.Status.SignedIn)
        }
    }
}
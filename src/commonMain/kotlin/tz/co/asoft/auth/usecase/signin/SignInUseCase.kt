package tz.co.asoft.auth.usecase.signin

import com.soywiz.krypto.SHA256
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.auth.Phone
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.persist.result.Result

open class SignInUseCase(
        private val repo: IAuthRepo,
        private val authStateUC: IAuthStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignInUseCase {
    enum class SignInType {
        email, phone
    }

    override suspend operator fun invoke(loginId: String, pwd: String) = coroutineScope {
        val password = SHA256.digest(pwd.toUtf8Bytes()).hex
        val type = if (loginId.contains("@")) {
            SignInType.email
        } else {
            SignInType.phone
        }
        Result.catching {
            when (type) {
                SignInType.email -> repo.load(Email(loginId), password)
                SignInType.phone -> repo.load(Phone(loginId), password)
            }?.also { updateStatusUC(it, User.Status.SignedIn) }
        }.also { res -> res.data?.let { authStateUC.liveUser.value = it } }
    }
}
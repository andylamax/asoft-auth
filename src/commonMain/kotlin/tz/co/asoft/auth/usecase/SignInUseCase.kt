package tz.co.asoft.auth.usecase

import com.soywiz.krypto.SHA256
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.persist.result.Result

open class SignInUseCase(
        private val repo: IAuthRepo,
        private val authStateUC: AuthStateUseCase,
        private val updateStatusUC: UpdateStatusUseCase
) {
    enum class SignInType {
        email, phone
    }

    suspend operator fun invoke(loginId: String, pwd: String) = coroutineScope {
        val password = SHA256.digest(pwd.toUtf8Bytes()).hex
        val type = if (loginId.contains("@")) {
            SignInType.email
        } else {
            SignInType.phone
        }
        Result.catching {
            when (type) {
                SignInType.email -> repo.emailSignIn(loginId, password)
                SignInType.phone -> repo.phoneSignIn(loginId, password)
            }?.also {
                updateStatusUC(it, User.Status.SignedIn)
            }
        }.also {
            authStateUC.liveUser.value = it.data
        }
    }
}
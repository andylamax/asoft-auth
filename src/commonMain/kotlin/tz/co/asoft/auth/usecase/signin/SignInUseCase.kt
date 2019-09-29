package tz.co.asoft.auth.usecase.signin

import tz.co.asoft.auth.User
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.auth.usecase.loaduser.ILoadUserUseCase
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase

open class SignInUseCase(
        private val loadUserUC: ILoadUserUseCase,
        private val authStateUC: IAuthStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignInUseCase {
    override suspend operator fun invoke(loginId: String, pwd: String) = loadUserUC(loginId, pwd).also { res ->
        res.data?.let {
            authStateUC.liveUser.value = it
            updateStatusUC(it, User.Status.SignedIn)
        }
    }
}
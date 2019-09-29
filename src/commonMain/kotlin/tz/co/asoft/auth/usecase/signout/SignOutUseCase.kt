package tz.co.asoft.auth.usecase.signout

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.usecase.authstate.IAuthStateUseCase
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase

open class SignOutUseCase(
        private val repo: IAuthRepo,
        private val authStateUC: IAuthStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignOutUseCase {

    override operator fun invoke() {
        val user = authStateUC.liveUser.value ?: return
        authStateUC.liveUser.value = null
        GlobalScope.launch { repo.deleteLocal() }
        updateStatusUC(user, User.Status.SignedOut)
    }
}
package tz.co.asoft.auth.usecase.signout

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.auth.usecase.userstate.IStateUseCase

open class SignOutUseCase(
        private val repo: IUsersRepo,
        private val userStateUC: IStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignOutUseCase {

    override suspend operator fun invoke() {
        val user = userStateUC.liveUser.value ?: return
        userStateUC.liveUser.value = null
        GlobalScope.launch { repo.deleteLocal() }
        updateStatusUC(user, User.Status.SignedOut)
    }
}
package tz.co.asoft.auth.usecase.signout

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.auth.usecase.userstate.IUserStateUseCase

open class SignOutUseCase(
        private val repo: IUsersRepo,
        private val userStateUC: IUserStateUseCase,
        private val updateStatusUC: IUpdateStatusUseCase
) : ISignOutUseCase {

    override suspend operator fun invoke() {
        val user = userStateUC.liveUser.value ?: return
        userStateUC.liveUser.value = null
        repo.deleteLocal()
        updateStatusUC(user, User.Status.SignedOut)
    }
}
package tz.co.asoft.auth.usecase

import tz.co.asoft.auth.User

open class SignOutUseCase(
        private val authStateUC: AuthStateUseCase,
        private val updateStatusUC: UpdateStatusUseCase
) {

    operator fun invoke() {
        val user = authStateUC.liveUser.value ?: return
        authStateUC.liveUser.value = null
        updateStatusUC(user, User.Status.SignedOut)
    }
}
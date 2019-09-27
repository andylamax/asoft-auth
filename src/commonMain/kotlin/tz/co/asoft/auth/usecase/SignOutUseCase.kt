package tz.co.asoft.auth.usecase

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User

open class SignOutUseCase(
        private val authStateUC: AuthStateUseCase,
        private val updateStatusUC: UpdateStatusUseCase
) {

    suspend operator fun invoke() = coroutineScope {
        val user = authStateUC.liveUser.value ?: return@coroutineScope
        authStateUC.liveUser.value = null
        launch { updateStatusUC(user, User.Status.SignedOut) }
    }
}
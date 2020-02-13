package tz.co.asoft.auth.usecase.userstate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.rx.LiveData

class UserStateUseCase(private val repo: IUsersRepo) : IUserStateUseCase {
    companion object {
        private var instance: IUserStateUseCase? = null
        @Deprecated("Just instantiate the class in a single{} method")
        fun getInstance(repo: IUsersRepo) = instance
                ?: UserStateUseCase(repo).also { instance = it }
    }

    override val liveUser = LiveData<User?>(null)

    override fun onChange(scope: CoroutineScope, action: (User?) -> Unit) {
        if (liveUser.value == null) {
            scope.launch { currentUser()?.let { liveUser.value = it } }
        }
        liveUser.onChange(scope, action)
    }

    override suspend fun currentUser() = liveUser.value ?: repo.loadLocalUser()?.takeIf {
        it.status == User.Status.SignedIn.name
    }
}
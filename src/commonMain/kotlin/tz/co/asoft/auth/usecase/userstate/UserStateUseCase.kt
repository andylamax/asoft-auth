package tz.co.asoft.auth.usecase.userstate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.rx.LiveData

class UserStateUseCase(private val repo: IUsersRepo) : IUserStateUseCase {
    companion object {
        private var instance: IUserStateUseCase? = null
        fun getInstance(repo: IUsersRepo) = instance
                ?: UserStateUseCase(repo).also { instance = it }
    }

    override val liveUser = LiveData<User?>(null)

    override fun onChange(scope: CoroutineScope, action: (User?) -> Unit) {
        if (liveUser.value == null) {
            scope.launch { liveUser.value = currentUser() }
        }
        liveUser.onChange(scope, action)
    }

    override suspend fun currentUser() = liveUser.value ?: repo.loadLocalUser()
}
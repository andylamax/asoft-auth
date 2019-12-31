package tz.co.asoft.auth.usecase.userstate

import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.rx.LiveData

class UserStateUseCase(private val repo: IUsersRepo) : IUserStateUseCase {
    companion object {
        private var instance: IUserStateUseCase? = null
        fun getInstance(repo: IUsersRepo) = instance ?: UserStateUseCase(repo)?.also { instance = it }
    }

    override val liveUser = LiveData<User?>(null)

    override suspend fun currentUser() = liveUser.value ?: repo.loadLocalUser()
}
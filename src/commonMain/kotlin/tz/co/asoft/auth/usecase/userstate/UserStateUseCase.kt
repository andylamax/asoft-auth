package tz.co.asoft.auth.usecase.userstate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.tools.channel.StatefulChannel

class UserStateUseCase(private val repo: IUsersRepo) : IUserStateUseCase {
    companion object {
        private var instance: IUserStateUseCase? = null
        fun getInstance(repo: IUsersRepo) = instance ?: UserStateUseCase(repo)?.also { instance = it }
    }

    override val liveUser = StatefulChannel<User?>()

    private val user: User? = null

    val live = flow {
        emit(user)
    }

    fun CoroutineScope.liveUser() = produce {
        send(User())
    }

    override suspend fun currentUser() = null //liveUser.value() ?: repo.loadLocalUser()
}
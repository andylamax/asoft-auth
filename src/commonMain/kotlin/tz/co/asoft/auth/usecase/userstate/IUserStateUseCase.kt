package tz.co.asoft.auth.usecase.userstate

import tz.co.asoft.auth.User
import tz.co.asoft.auth.tools.channel.StatefulChannel
import tz.co.asoft.rx.LiveData

interface IUserStateUseCase {
    val liveUser: LiveData<User?>
    suspend fun currentUser(): User?
}
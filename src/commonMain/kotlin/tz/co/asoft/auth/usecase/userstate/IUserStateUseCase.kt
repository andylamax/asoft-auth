package tz.co.asoft.auth.usecase.userstate

import tz.co.asoft.auth.User
import tz.co.asoft.auth.tools.channel.StatefulChannel

interface IUserStateUseCase {
    val liveUser: StatefulChannel<User?>
    suspend fun currentUser(): User?
}
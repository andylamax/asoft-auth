package tz.co.asoft.auth.usecase.userstate

import kotlinx.coroutines.CoroutineScope
import tz.co.asoft.auth.User
import tz.co.asoft.rx.LiveData

interface IUserStateUseCase {
    val liveUser: LiveData<User?>
    suspend fun currentUser(): User?
    fun onChange(scope: CoroutineScope, action: (User?) -> Unit)
}
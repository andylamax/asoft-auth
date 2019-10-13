package tz.co.asoft.auth.usecase.authstate

import tz.co.asoft.auth.User
import tz.co.asoft.rx.lifecycle.ILifeCycle
import tz.co.asoft.rx.lifecycle.LiveData
import tz.co.asoft.rx.lifecycle.Observer

interface IAuthStateUseCase {
    val liveUser: LiveData<User?>
    suspend fun onAuthStateChanged(lifeCycle: ILifeCycle, onChange: (User?) -> Unit): Observer<User?>
    suspend fun currentUser(): User?
}
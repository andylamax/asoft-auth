package tz.co.asoft.auth.usecase.authstate

import tz.co.asoft.auth.User
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData
import tz.co.asoft.rx.lifecycle.Observer

interface IAuthStateUseCase {
    val liveUser: LiveData<User?>
    suspend fun onAuthStateChanged(lifeCycle: LifeCycle, onChange: (User?) -> Unit): Observer<User?>
}
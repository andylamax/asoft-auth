package tz.co.asoft.auth.usecase.authstate

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.persist.tools.Singleton
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

open class AuthStateUseCase private constructor(private val repo: IAuthRepo) : IAuthStateUseCase {
    companion object : Singleton<IAuthRepo, AuthStateUseCase>({ AuthStateUseCase((it)) })

    override val liveUser = LiveData<User?>(null)

    override suspend fun onAuthStateChanged(lifeCycle: LifeCycle, onChange: (User?) -> Unit) = coroutineScope {
        if (liveUser.value == null) {
            launch { liveUser.value = repo.loadLocalUser() }
        }
        liveUser.observe(lifeCycle, onChange)
    }
}
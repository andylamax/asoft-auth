package tz.co.asoft.auth.usecase

import com.soywiz.klock.DateTime
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.AuthAbstractRepo
import tz.co.asoft.auth.viewmodel.AuthViewModel
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Singleton
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData
import tz.co.asoft.rx.lifecycle.Observer

class SigningUseCase private constructor(private val repo: AuthAbstractRepo) {
    companion object : Singleton<AuthAbstractRepo, SigningUseCase>({ SigningUseCase(it) })

    val liveUser = LiveData<User?>(null)

    enum class SignInType {
        email, phone
    }

    suspend fun signIn(loginId: String, pwd: String) = coroutineScope {
        val type = if (loginId.contains("@")) {
            SignInType.email
        } else {
            SignInType.phone
        }
        Result.catching {
            when (type) {
                SignInType.email -> repo.emailSignIn(loginId, pwd)
                SignInType.phone -> repo.phoneSignIn(loginId, pwd)
            }?.also {
                it.updateStatus(User.Status.SignedIn)
                launch { repo.edit(it) }
            }
        }
    }

    suspend fun signOut() = coroutineScope {
        val user = liveUser.value ?: return@coroutineScope
        liveUser.value = null
        user.updateStatus(User.Status.SignedOut)
        launch { repo.edit(user) }
    }

    suspend fun onAuthStateChanged(lifeCycle: LifeCycle, onChange: (User?) -> Unit) = coroutineScope {
        val obs = liveUser.observe(lifeCycle, onChange)
        if (liveUser.value == null) {
            liveUser.value = repo.loadLocalUser()
        }
        obs
    }

    private fun User.updateStatus(s: User.Status) {
        lastSeen = DateTime.nowUnixLong()
        status = s.name
    }
}
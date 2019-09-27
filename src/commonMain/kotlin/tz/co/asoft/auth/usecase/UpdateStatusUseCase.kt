package tz.co.asoft.auth.usecase

import com.soywiz.klock.DateTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result

open class UpdateStatusUseCase(private val repo: Repo<User>) {
    operator fun invoke(u: User, s: User.Status) {
        u.lastSeen = DateTime.nowUnixLong()
        u.status = s.name
        GlobalScope.launch { repo.edit(u) }
    }
}
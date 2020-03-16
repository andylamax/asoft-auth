package tz.co.asoft.auth.usecase.updatestatus

import tz.co.asoft.klock.DateTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.auth.User
import tz.co.asoft.auth.repo.IUsersRepo
import tz.co.asoft.auth.usecase.updatestatus.IUpdateStatusUseCase
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result

open class UpdateStatusUseCase(private val repo: IUsersRepo) : IUpdateStatusUseCase {
    override operator fun invoke(u: User, s: User.Status) {
        u.lastSeen = DateTime.nowUnixLong()
        u.status = s.name
        GlobalScope.launch {
            if (s == User.Status.SignedOut) {
                repo.deleteLocal()
            }
            repo.remoteDao.edit(u)
        }
    }
}
package tz.co.asoft.auth.usecase.loaduser

import com.soywiz.krypto.SHA256
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.Phone
import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserRef
import tz.co.asoft.auth.repo.IAuthRepo
import tz.co.asoft.auth.tools.hex.hex
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

class LoadUserUseCase(val repo: IAuthRepo) : ILoadUserUseCase {

    override suspend fun invoke(loginId: String, pwd: String) = Result.catching {
        val xpwd = SHA256.digest(pwd.toUtf8Bytes()).hex
        if (loginId.contains("@")) {
            repo.load(Email(loginId), xpwd)
        } else {
            repo.load(Phone(loginId), xpwd)
        }
    }

    override suspend fun invoke(uid: Any) = repo.loadCatching(uid)

    override suspend fun invoke(ref: UserRef) = repo.loadCatching(ref.uid)
}
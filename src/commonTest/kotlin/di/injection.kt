package di

import dao.UserAccountsDao
import dao.UsersDao
import dao.UsersLocalDao
import tz.co.asoft.auth.UserAccount
import tz.co.asoft.auth.repo.UsersRepo
import tz.co.asoft.auth.usecase.registeruser.RegisterUserUseCase
import tz.co.asoft.auth.usecase.signin.SignInUseCase
import tz.co.asoft.auth.usecase.updatestatus.UpdateStatusUseCase
import tz.co.asoft.auth.usecase.userstate.UserStateUseCase
import tz.co.asoft.persist.dao.Cache
import tz.co.asoft.persist.di.dao
import tz.co.asoft.persist.di.repo
import tz.co.asoft.persist.di.single
import tz.co.asoft.persist.repo.CachedRepo

object injection {
    object dao {
        fun users() = dao { UsersDao() }
        fun usersLocalDao() = single { UsersLocalDao() }
        fun accounts() = dao { UserAccountsDao() }
    }

    object repo {
        fun users() = repo { UsersRepo(dao.users(), dao.usersLocalDao()) }
        fun accounts() = repo { CachedRepo(dao.accounts()) }
    }

    object useCase {
        fun registerUser() = RegisterUserUseCase(repo.users(), repo.accounts())
        fun userState() = UserStateUseCase.getInstance(repo.users())
        fun updateStatus() = UpdateStatusUseCase(repo.users())
        fun signIn() = SignInUseCase(repo.users(), userState(), updateStatus())
    }
}
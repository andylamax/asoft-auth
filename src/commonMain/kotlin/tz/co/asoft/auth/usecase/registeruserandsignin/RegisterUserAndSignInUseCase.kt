package tz.co.asoft.auth.usecase.registeruserandsignin

import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserAccount
import tz.co.asoft.auth.usecase.registeruser.IRegisterUserUseCase
import tz.co.asoft.auth.usecase.signin.ISignInUseCase
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.result.catching

open class RegisterUserAndSignInUseCase(
        private val registerUserUC: IRegisterUserUseCase,
        private val signInUC: ISignInUseCase
) : IRegisterUserAndSignInUseCase {
    override suspend operator fun invoke(user: User, ua: UserAccount?) = catching {
        val pwd = user.password ?: "123456"
        registerUserUC(user, ua).respond()
        signInUC(user.emails.first(), pwd).respond()
    }
}
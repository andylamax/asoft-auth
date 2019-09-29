package tz.co.asoft.auth.usecase.registeruserandsignin

import tz.co.asoft.auth.User
import tz.co.asoft.auth.usecase.registeruser.IRegisterUserUseCase
import tz.co.asoft.auth.usecase.signin.ISignInUseCase
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

open class RegisterUserAndSignInUseCase(
        private val registerUserUC: IRegisterUserUseCase,
        private val signInUC: ISignInUseCase
) : IRegisterUserAndSignInUseCase {
    override suspend operator fun invoke(u: User): Result<User> {
        val pwd = u.password
        return try {
            registerUserUC(u).respond()
            signInUC(u.emails.first(), pwd)
        } catch (c: Cause) {
            Result.failure(c)
        }
    }
}
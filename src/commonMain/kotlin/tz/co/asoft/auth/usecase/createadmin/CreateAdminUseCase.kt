package tz.co.asoft.auth.usecase.createadmin

import tz.co.asoft.auth.User
import tz.co.asoft.auth.usecase.registeruser.IRegisterUserUseCase

open class CreateAdminUseCase(private val registerUserUC: IRegisterUserUseCase) : ICreateAdminUseCase {

    private val admin = User().apply {
        name = "System Admin"
        emails.add("admin@admin.com")
        phones.add("255000000000")
        username = "administrator"
        password = "adminadmin"
        permits.add(":dev")
    }

    override suspend fun invoke() = registerUserUC(admin, null)
}
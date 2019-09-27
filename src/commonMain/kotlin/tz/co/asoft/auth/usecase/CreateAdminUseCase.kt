package tz.co.asoft.auth.usecase

import tz.co.asoft.auth.User
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.result.Result

open class CreateAdminUseCase(private val repo: Repo<User>) {
    private val admin = User().apply {
        name = "System Admin"
        emails.add("admin@admin.com")
        phones.add("0000000000")
        username = "administrator"
        password = "adminadmin"
        permits.add(":dev")
    }

    suspend operator fun invoke() = Result.catching {
        repo.create(admin)
    }
}
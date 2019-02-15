package com.asofttz.auth.data.viewmodal

import com.asofttz.auth.User
import com.asofttz.auth.data.repo.UserRepo

class AuthViewModal(private val repo: UserRepo) {
    suspend fun create(user: User) = repo.create(user)
    suspend fun edit(user: User) = repo.edit(user)
    suspend fun loadAll() = repo.loadAll()
    suspend fun load(id: Int) = repo.load(id)
    suspend fun filter(predicate: (User) -> Boolean) = repo.filter(predicate)
    suspend fun login(username: String, password: String): User? = repo.login(username, password)
    suspend fun logOut(user: User): Boolean = repo.logOut(user)
    suspend fun toggleAccess(user: User) = edit(user.apply { isBlocked = !isBlocked })
    suspend fun delete(user: User) = repo.delete(user)
}
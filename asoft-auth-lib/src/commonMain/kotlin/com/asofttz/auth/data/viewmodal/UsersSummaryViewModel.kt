package com.asofttz.auth.data.viewmodal

import com.asofttz.auth.data.repo.UserRepo

class UsersSummaryViewModel(private val repo: UserRepo) {
    suspend fun allUsers() = repo.loadAll().value
    suspend fun blockedUsers() = allUsers().filter { it.isBlocked }
    suspend fun activeUsers() = allUsers()
}
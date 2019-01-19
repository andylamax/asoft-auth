package com.asofttz.auth.data.viewmodal

import com.asofttz.auth.data.repo.AuthRepo

class UsersSummaryViewModel(private val repo: AuthRepo) {
    private val observedUsers = repo.getAll()
    fun allUsers() = observedUsers.value
    fun blockedUsers() = allUsers().filter { it.isBlocked }
    fun activeUsers() = allUsers()
}
package com.asofttz.auth.data.viewmodal

import com.asofttz.auth.User
import com.asofttz.auth.data.repo.AuthRepo
import com.asofttz.rx.ObservableList
import kotlinx.serialization.json.JSON

class AuthViewModal(private val repo: AuthRepo) {
    fun add(user: User) = repo.add(user)
    fun edit(user: User) = repo.edit(user)
    fun getAll(): ObservableList<User> = repo.getAll()
    fun get(id: Long): User = repo.get(id)
    fun filter(key: String, users: MutableList<User>): List<User> = users.filter {
        val userJson = JSON.stringify(User.serializer(), it).toLowerCase().replace(" ", "")
        userJson.indexOf(key.replace(" ", "").toLowerCase()) >= 0
    }

    fun login(username: String, password: String): User? = repo.login(username, password)
    fun logOut(user: User): Boolean = repo.logOut(user)
    fun toggleAccess(user: User) = edit(user.apply { isBlocked = !isBlocked })
    fun delete(user: User) = repo.delete(user)
}
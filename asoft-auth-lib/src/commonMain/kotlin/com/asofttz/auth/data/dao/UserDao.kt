package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.rx.ObservableList

abstract class UserDao {
    var cached_users = ObservableList<User>()

    abstract fun add(user: User)
    abstract fun edit(user: User)
    abstract fun getAll(): ObservableList<User>
    abstract fun get(id: Long): User
    abstract fun login(username: String, password: String): User?
    abstract fun logOut(user: User) : Boolean
}
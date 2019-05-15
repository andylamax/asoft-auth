package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.persist.Dao

abstract class UserAbstractDao : Dao<User>() {
    abstract suspend fun login(username: String, password: String): User?
    abstract suspend fun logOut(user: User): Boolean
}
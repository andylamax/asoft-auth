package com.asofttz.auth.data.repo

import com.asofttz.auth.User
import com.asofttz.auth.data.dao.UserAbstractDao
import com.asofttz.persist.Repo

abstract class UserAbstractRepo(private val dao: UserAbstractDao) : Repo<User>(dao) {
    open suspend fun login(username: String,password: String) = dao.login(username, password)
    open suspend fun logOut(user: User) = dao.logOut(user)
}
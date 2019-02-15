package com.asofttz.auth.data.repo

import com.asofttz.auth.User
import com.asofttz.auth.data.dao.UserDao
import com.asofttz.persist.Repo

class UserRepo private constructor(private val dao: UserDao) : Repo<User>(dao) {
    companion object {
        private var instance: UserRepo? = null
        fun getInstance(userDao: UserDao): UserRepo {
            if (instance == null) {
                instance = UserRepo(userDao)
            }
            return instance!!
        }
    }
    suspend fun login(username: String,password: String) = dao.login(username, password)
    suspend fun logOut(user: User) = dao.logOut(user)
}
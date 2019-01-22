package com.asofttz.auth.data.repo

import com.asofttz.auth.User
import com.asofttz.auth.data.dao.UserDao
import com.asofttz.rx.ObservableList

class AuthRepo private constructor(private val userDao: UserDao) {
    companion object {
        private var instance: AuthRepo? = null
        fun getInstance(userDao: UserDao): AuthRepo {
            if (instance == null) {
                instance = AuthRepo(userDao)
            }
            return instance!!
        }
    }

    fun add(user: User) = userDao.add(user)
    fun edit(user: User) = userDao.edit(user)
    fun getAll(): ObservableList<User> = userDao.getAll()
    fun get(id: Long): User = userDao.get(id)
    fun login(username: String, password: String): User? = userDao.login(username, password)
    fun logOut(user: User): Boolean = userDao.logOut(user)
    fun delete(user: User) = userDao.delete(user)
}
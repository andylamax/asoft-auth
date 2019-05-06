package com.asofttz.auth.data.repo

import com.asofttz.auth.data.dao.UserAbstractDao

class UserRepo private constructor(private val dao: UserAbstractDao) : UserAbstractRepo(dao) {
    companion object {
        private var instance: UserAbstractRepo? = null
        fun getInstance(userDao: UserAbstractDao): UserAbstractRepo {
            if (instance == null) {
                instance = UserRepo(userDao)
            }
            return instance!!
        }
    }
}
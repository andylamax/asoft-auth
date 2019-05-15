package com.asofttz.auth.data.repo

import com.asofttz.auth.data.dao.UserAbstractDao
import com.asofttz.persist.Singleton

class UserRepo private constructor(private val dao: UserAbstractDao) : UserAbstractRepo(dao) {
    companion object : Singleton<UserAbstractDao, UserAbstractRepo>({ UserRepo(it) })
}
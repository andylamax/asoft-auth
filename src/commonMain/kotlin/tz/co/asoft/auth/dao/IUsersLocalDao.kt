package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User
import tz.co.asoft.persist.dao.ICache
import tz.co.asoft.persist.dao.IDao

interface IUsersLocalDao : ICache<User> {
    suspend fun load(): User?
    suspend fun delete()
}
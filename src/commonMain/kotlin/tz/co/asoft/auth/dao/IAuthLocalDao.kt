package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User
import tz.co.asoft.persist.dao.IDao

interface IAuthLocalDao : IDao<User> {
    suspend fun load(): User?
    suspend fun delete()
}
package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User

interface IAuthLocalDao {
    suspend fun load(): User?
    suspend fun delete()
}
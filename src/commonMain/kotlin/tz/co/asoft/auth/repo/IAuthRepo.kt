package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.IAuthDao

interface IAuthRepo : IAuthDao {
    suspend fun saveToLocal(u: User): User?

    suspend fun loadLocalUser(): User?

    suspend fun removeLocal()

    suspend fun userWithEmailExists(emails: List<String>) = false

    suspend fun userWithPhoneExists(phones: List<String>) = false
}
package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.IAuthDao
import tz.co.asoft.persist.repo.IRepo

interface IAuthRepo : IRepo<User>, IAuthDao {
    suspend fun saveToLocal(u: User): User?

    suspend fun loadLocalUser(): User?

    suspend fun deleteLocal()

    suspend fun userWithEmailExists(emails: List<String>) = false

    suspend fun userWithPhoneExists(phones: List<String>) = false
}
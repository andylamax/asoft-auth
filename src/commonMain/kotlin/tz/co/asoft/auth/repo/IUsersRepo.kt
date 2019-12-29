package tz.co.asoft.auth.repo

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.IUsersDao
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.repo.ITwinRepo
import tz.co.asoft.persist.repo.TwinRepo

interface IUsersRepo : ITwinRepo<User>, IUsersDao {

    suspend fun loadLocalUser(): User?

    suspend fun deleteLocal()

    suspend fun userWithEmailExists(emails: List<String>) = false

    suspend fun userWithPhoneExists(phones: List<String>) = false
}
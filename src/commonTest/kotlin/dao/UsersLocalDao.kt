package dao

import tz.co.asoft.auth.User
import tz.co.asoft.auth.dao.IUsersLocalDao

class UsersLocalDao : IUsersLocalDao {
    override var data: MutableMap<String, User>? = mutableMapOf()
    private var theUser: User? = null
    override suspend fun load(): User? = theUser

    override suspend fun delete() {
        theUser = null
    }
}
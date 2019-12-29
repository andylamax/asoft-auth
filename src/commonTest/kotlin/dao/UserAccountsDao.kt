package dao

import tz.co.asoft.auth.UserAccount
import tz.co.asoft.persist.dao.Cache

class UserAccountsDao : Cache<UserAccount>() {
    override suspend fun create(t: UserAccount): UserAccount {
        t.uid = all().size.toString()
        return super.create(t)
    }
}
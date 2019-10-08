package tz.co.asoft.auth.dao

import kotlinx.serialization.json.Json
import tz.co.asoft.auth.User
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.storage.Storage
import tz.co.asoft.platform.core.Ctx

open class AuthLocalDao(ctx: Ctx, name: String) : Dao<User>(), IAuthLocalDao {
    private val db = Storage(ctx, name)
    private val serializer = User.serializer()

    override suspend fun create(user: User): User? {
        db.set("device_user", Json.stringify(serializer, user))
        return user
    }

    override suspend fun load(): User? {
        val json = db.get("device_user") ?: return null
        return Json.parse(serializer, json)
    }

    override suspend fun delete() {
        db.remove("device_user")
    }
}
package tz.co.asoft.auth.dao

import kotlinx.serialization.json.Json
import tz.co.asoft.auth.User
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.storage.Storage
import tz.co.asoft.platform.Ctx
import tz.co.asoft.rx.lifecycle.LiveData

open class AuthLocalDao private constructor(ctx: Ctx, name: String) : Dao<User>(), IAuthLocalDao {

    private val db = Storage(ctx, name)
    private val serializer = User.serializer()

    companion object {
        private var instance: IAuthLocalDao? = null
        /**
         * It is advices to use auth package name for this one
         */
        fun getInstance(ctx: Ctx, name: String) = instance ?: AuthLocalDao(ctx, name).also {
            instance = it
        }
    }

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
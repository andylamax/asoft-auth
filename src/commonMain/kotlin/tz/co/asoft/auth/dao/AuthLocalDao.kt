package tz.co.asoft.auth.dao

import kotlinx.serialization.json.Json
import tz.co.asoft.auth.User
import tz.co.asoft.persist.lock.Lock
import tz.co.asoft.persist.storage.Storage

open class AuthLocalDao private constructor(ctx: Any, name: String) : AuthAbstractLocalDao() {

    private val db = Storage(ctx, name)

    companion object {
        private var instance: AuthAbstractLocalDao? = null
        private val lock = Lock()
        /**
         * It is advices to use auth package name for this one
         */
        fun getInstance(ctx: Any, name: String) = instance ?: AuthLocalDao(ctx, name).also {
            instance = it
        }
    }

    override suspend fun save(user: User): User? {
        db.set("device_user", Json.stringify(serializer, user))
        return user
    }

    override suspend fun load(): User? {
        val json = db.get("device_user") ?: return null
        return Json.parse(serializer, json)
    }

    override suspend fun signOut() {
        db.clear()
    }
}
package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User

abstract class AuthAbstractLocalDao {
    val serializer = User.serializer()
    abstract suspend fun load(): User?
    abstract suspend fun save(user: User): User?
    abstract suspend fun signOut()
}
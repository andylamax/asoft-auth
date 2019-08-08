package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User
import tz.co.asoft.persist.dao.PaginatedDao

abstract class AuthAbstractDao : PaginatedDao<User>() {
    protected val serializer = User.serializer()
    abstract suspend fun uploadPhoto(user: User, photoRef: Any): User?
    abstract suspend fun emailSignIn(email: String, pwd: String): User?
    abstract suspend fun phoneSignIn(phone: String, pwd: String): User?
    abstract suspend fun signOut(user: User): User?
}
package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.dao.PaginatedDao

abstract class AuthAbstractDao : Dao<User>() {
    val serializer = User.serializer()
    abstract suspend fun uploadPhoto(user: User, photo: File): User?
    abstract suspend fun emailSignIn(email: String, pwd: String): User?
    abstract suspend fun phoneSignIn(phone: String, pwd: String): User?
}
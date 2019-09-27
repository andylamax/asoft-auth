package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.dao.PaginatedDao

interface IAuthDao {
    suspend fun uploadPhoto(user: User, photo: File): User?
    suspend fun emailSignIn(email: String, pwd: String): User?
    suspend fun phoneSignIn(phone: String, pwd: String): User?
}
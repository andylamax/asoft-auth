package tz.co.asoft.auth.dao

import tz.co.asoft.auth.User
import tz.co.asoft.auth.Email
import tz.co.asoft.auth.Phone
import tz.co.asoft.io.file.File
import tz.co.asoft.persist.dao.IDao

interface IAuthDao : IDao<User> {
    suspend fun uploadPhoto(user: User, photo: File): User?
    suspend fun load(email: Email, pwd: String): User?
    suspend fun load(phone: Phone, pwd: String): User?
}
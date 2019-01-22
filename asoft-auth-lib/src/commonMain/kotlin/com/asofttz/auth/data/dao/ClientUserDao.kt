package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.auth.data.AuthDataSourceConfig
import com.asofttz.date.DateFactory
import com.asofttz.date.Mil
import com.asofttz.rx.ObservableList
import kotlin.math.PI
import kotlin.math.sin

class ClientUserDao private constructor(private val config: AuthDataSourceConfig) : UserDao() {

    companion object {
        private var instance: UserDao? = null
        fun getInstance(config: AuthDataSourceConfig): UserDao {
            if (instance == null) {
                instance = ClientUserDao(config)
                repeat(90) {
                    instance?.add(User.fakeUser)
                }
            }
            return instance!!
        }
    }

    override fun add(user: User) {
        cached_users.add(user)
    }

    override fun edit(user: User) {
        cached_users[user.id!!.toInt()] = user
    }

    override fun getAll(): ObservableList<User> {
        return cached_users
    }

    override fun get(id: Long): User {
        return User.fakeUser
    }

    override fun login(username: String, password: String): User? {
        return User.fakeUser.apply {
            fullname = "Anderson Lameck"
            this.username = "andylamax"
            permits.apply {
                add(":dev")
            }
        }
    }

    override fun logOut(user: User): Boolean {
        return true
    }

    override fun delete(user: User) {
        cached_users.remove(user)
    }
}
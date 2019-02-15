package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.persist.DataSourceConfig
import com.asofttz.rx.ObservableList
import kotlinx.coroutines.delay

class ClientUserDao private constructor(private val config: DataSourceConfig) : UserDao() {

    companion object {
        private var instance: UserDao? = null
        fun getInstance(config: DataSourceConfig): UserDao {
            if (instance == null) {
                instance = ClientUserDao(config)
            }
            return instance!!
        }
    }

    override suspend fun create(t: User): Boolean {
        delay(1000)
        cached.add(t)
        return true
    }

    override suspend fun edit(t: User): Boolean {
        delay(1000)
        return false
    }

    override suspend fun load(id: Int): User? {
        delay(1000)
        return cached.value.getOrNull(id)
    }

    override suspend fun loadAll(): ObservableList<User> {
        if(cached.size==0) {
            delay(2000)
            repeat(23) {
                cached.value.add(User.fakeUser)
            }
        }
        return cached
    }

    override suspend fun login(username: String, password: String): User? {
        delay(1000)
        return User.fakeUser.apply {
            fullname = "Anderson Lameck"
            this.username = "andylamax"
            permits.apply {
                add(":dev")
            }
        }
    }

    override suspend fun logOut(user: User): Boolean {
        delay(1000)
        return true
    }

    override suspend fun delete(user: User):Boolean {
        delay(1000)
        cached.remove(user)
        return true
    }
}
package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.persist.DataSourceConfig
import com.asofttz.persist.Singleton
import com.asofttz.rx.ObservableList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class ClientUserDao private constructor(private val config: DataSourceConfig) : UserAbstractDao() {

    companion object : Singleton<DataSourceConfig, UserAbstractDao>({ ClientUserDao(it) })

    override suspend fun create(t: User): User? {
        delay(1000)
        cached.add(t)
        return t
    }

    override suspend fun edit(t: User): User? {
        delay(1000)
        return t
    }

    override suspend fun load(id: Int): User? {
        delay(1000)
        return cached.value.firstOrNull { it.id == id.toLong() }
    }

    override suspend fun loadAll(): ObservableList<User> = coroutineScope {
        if (cached.size == 0) {
            val users = mutableListOf<User>()
            delay(2000)
            repeat(23) {
                users.add(User.fakeUser)
            }
            cached.value = users
        }
        cached
    }

    override suspend fun login(username: String, password: String): User? {
        delay(1000)
        return loadAll().value.firstOrNull {
            it.username == username && it.password == password
        }
    }

    override suspend fun logOut(user: User): Boolean {
        delay(1000)
        return true
    }

    override suspend fun delete(user: User): User? {
        delay(1000)
        cached.remove(user)
        return user
    }
}
package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.persist.DataSourceConfig
import com.asofttz.rx.ObservableList
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory

class ServerUserDao private constructor(config: DataSourceConfig) : UserAbstractDao() {
    companion object {
        private var instance: UserAbstractDao? = null
        fun getInstance(config: DataSourceConfig): UserAbstractDao {
            synchronized(this) {
                if (instance == null) {
                    instance = ServerUserDao(config)
                }
                return instance!!
            }
        }
    }

    private val configuration = Configuration.Builder()
            .uri(config.url)
            .credentials(config.username, config.password)
            .build()

    private val sessionFactory = SessionFactory(configuration,
            User::class.java.`package`.name
    )

    override suspend fun create(user: User) = synchronized(this) {
        with(sessionFactory.openSession()) {
            save(user)
            clear()
            true
        }
    }

    override suspend fun edit(user: User) = synchronized(this) {
        with(sessionFactory.openSession()) {
            save(user)
            clear()
            true
        }
    }

    override suspend fun loadAll(): ObservableList<User> = synchronized(this) {
        return with(sessionFactory.openSession()) {
            ObservableList<User>().apply {
                value = loadAll(User::class.java).toMutableList()
            }
        }
    }

    override suspend fun load(id: Int): User {
        return User.fakeUser
    }

    override suspend fun login(username: String, password: String): User? {
        return User.fakeUser
    }

    override suspend fun logOut(user: User): Boolean {
        return true
    }

    override suspend fun delete(user: User): Boolean {
        return false
    }
}
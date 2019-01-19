package com.asofttz.auth.data.dao

import com.asofttz.auth.User
import com.asofttz.auth.data.AuthDataSourceConfig
import com.asofttz.rx.ObservableList
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory

class ServerUserDao private constructor(config: AuthDataSourceConfig) : UserDao() {
    companion object {
        private var instance: UserDao? = null
        fun getInstance(config: AuthDataSourceConfig): UserDao {
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

    override fun add(user: User) = synchronized(this) {
        with(sessionFactory.openSession()) {
            save(user)
            clear()
        }
    }

    override fun edit(user: User) = synchronized(this) {
        with(sessionFactory.openSession()) {
            save(user)
            clear()
        }
    }

    override fun getAll(): ObservableList<User> = synchronized(this) {
        return with(sessionFactory.openSession()) {
            ObservableList<User>().apply {
                value = loadAll(User::class.java).toMutableList()
            }
        }
    }

    override fun get(id: Long): User {
        return User.fakeUser
    }

    override fun login(username: String, password: String): User? {
        return User.fakeUser
    }

    override fun logOut(user: User): Boolean {
        return true
    }
}
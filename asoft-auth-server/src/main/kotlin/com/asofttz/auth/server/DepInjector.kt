package com.asofttz.auth.server

import com.asofttz.auth.data.AuthDataSourceConfig
import com.asofttz.auth.data.dao.ServerUserDao
import com.asofttz.auth.data.dao.UserDao
import com.asofttz.auth.data.repo.AuthRepo
import com.asofttz.auth.data.viewmodal.AuthViewModal
import com.asofttz.logging.Logger
import com.asofttz.logging.data.dao.ServerLogDao
import com.asofttz.logging.data.db.LogDataSourceConfig
import com.asofttz.logging.data.repo.LogRepo
import com.asofttz.logging.data.viewmodal.LogViewModal
import java.io.File
import java.lang.Exception

object injection {

    val logger = Logger("auth-server")

    private val config = AuthDataSourceConfig().apply {
        url = "bolt://localhost:9012"
    }

    init {
        val classLoader = javaClass.classLoader
        try {
            val file = File(classLoader.getResource("credentials.txt").file)

            println("\n\n\n\n")
            logger.i("Loading Credentials")

            val line = file.readLines()
            config.apply {
                try {
                    username = line[0].split(" ")[0]
                    password = line[0].split(" ")[1]
                    logger.i("Success")
                } catch (e: Exception) {
                    logger.f("Failed getting credentials from file: $e")
                }
            }
        } catch (e: Exception) {
            println("\n\n\n\n")
            logger.e("Couldn't find credentials.txt file\n\n\n\n")
            throw Exception("No Credentials File")
        }
    }

    private val userDao: UserDao = ServerUserDao.getInstance(config)

    private val authRepo = AuthRepo.getInstance(userDao)

    val authViewModal = AuthViewModal(authRepo)
}
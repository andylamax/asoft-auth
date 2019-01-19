package com.asofttz.auth.server

import com.asofttz.auth.User
import com.asofttz.date.Date
import com.asofttz.date.DateSerializer
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.serialization.context.SimpleModule
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(CORS) {
        anyHost()
    }

    val logger = injection.logger

    routing {
        get("/users") {
            with(injection.authViewModal) {
                call.respondJson(JSON.indented.stringify(User.serializer().list, getAll().value))
            }
        }

        get("/test") {
            with(injection.authViewModal) {
                val user = User.fakeUser
                add(user)
                call.respondJson(JSON.indented.stringify(User.serializer(), user))
            }
        }
    }
}

suspend fun ApplicationCall.respondJson(json: String) = respondText(json, ContentType.Application.Json)
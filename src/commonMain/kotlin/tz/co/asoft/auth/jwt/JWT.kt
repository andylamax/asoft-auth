package tz.co.asoft.auth.jwt

import com.soywiz.krypto.SHA256
import io.ktor.util.InternalAPI
import io.ktor.util.decodeBase64String
import io.ktor.util.encodeBase64
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.tools.hex.hex

class JWT<T> {
    var header = Header()
    var payload: T? = null
}

@UseExperimental(InternalAPI::class)
fun String.encodeBase64Url() = encodeBase64().replace("=", "")

@UseExperimental(InternalAPI::class)
@ImplicitReflectionSerializer
inline fun <reified T : Any> signJWT(header: Header = Header(), payload: T, secret: String): String {
    val headerHex = Json.stringify(Header.serializer(), header).encodeBase64Url()
    val payloadHex = Json.stringify(T::class.serializer(), payload).encodeBase64Url()
    val hash = SHA256.digest("$headerHex.$payloadHex.$secret".toUtf8Bytes()).hex
    return "$headerHex.$payloadHex.$hash"
}

@ImplicitReflectionSerializer
@UseExperimental(InternalAPI::class)
inline fun <reified T : Any> verifyJWT(token: String, secret: String): JWT<T>? {
    val (header, payload) = token.split(".")
    val h = Json.parse(Header.serializer(), header.decodeBase64String())
    val p = Json.parse(T::class.serializer(), payload.decodeBase64String())

    if (token != signJWT(h, p, secret)) return null

    return JWT<T>().also {
        it.header = h
        it.payload = p
    }
}
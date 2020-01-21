package tz.co.asoft.auth.jwt

import com.soywiz.krypto.SHA256
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlinx.serialization.toUtf8Bytes
import tz.co.asoft.auth.tools.hex.hex

class JWT<T> {
    var header = Header()
    var payload: T? = null
}

@ImplicitReflectionSerializer
@ExperimentalStdlibApi
inline fun <reified T : Any> signJWT(header: Header = Header(), payload: T, secret: String): String {
    val headerHex = Json.stringify(Header.serializer(), header).encodeBase64ToString()
    val payloadHex = Json.stringify(T::class.serializer(), payload).encodeBase64ToString()
    val hash = SHA256.digest("$headerHex.$payloadHex.$secret".toUtf8Bytes()).hex
    return "$headerHex.$payloadHex.$hash"
}

@ImplicitReflectionSerializer
@ExperimentalStdlibApi
inline fun <reified T : Any> verifyJWT(token: String, secret: String): JWT<T>? {
    val (header, payload) = token.split(".")
    val h = Json.parse(Header.serializer(), header.decodeBase64())
    val p = Json.parse(T::class.serializer(), payload.decodeBase64())

    if (token != signJWT(h, p, secret)) return null

    return JWT<T>().also {
        it.header = h
        it.payload = p
    }
}
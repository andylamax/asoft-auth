package tz.co.asoft.auth.jwt

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import tz.co.asoft.krypto.SHA256
import tz.co.asoft.krypto.hex

class JWT<T> {
    var header = Header()
    var payload: T? = null
}

@ImplicitReflectionSerializer
@ExperimentalStdlibApi
inline fun <reified T : Any> signJWT(header: Header = Header(), payload: T, secret: String): String {
    val headerHex = Json.stringify(Header.serializer(), header).encodeBase64ToString()
    val payloadHex = Json.stringify(T::class.serializer(), payload).encodeBase64ToString()
    val hash = SHA256.digest("$headerHex.$payloadHex.$secret".encodeToByteArray()).hex
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
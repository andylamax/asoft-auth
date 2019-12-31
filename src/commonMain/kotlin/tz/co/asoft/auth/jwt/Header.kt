package tz.co.asoft.auth.jwt

import kotlinx.serialization.Serializable

@Serializable
class Header {
    var alg = Algorithm.HS256.name
    var typ = Type.JWT.name

    enum class Algorithm {
        HS256, SHA256
    }

    enum class Type {
        JWT
    }
}
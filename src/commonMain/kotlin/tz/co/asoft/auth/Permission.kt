package tz.co.asoft.auth

import kotlinx.serialization.Serializable

@Serializable
class Permission {
    var account = UserAccountRef()
    var claims = mutableListOf<String>()
}
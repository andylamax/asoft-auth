package tz.co.asoft.auth

import kotlinx.serialization.Serializable

@Serializable
class UserRef {
    var id: Long? = null
    var uid = ""
    var name = ""
    var photoUrl = ""
}
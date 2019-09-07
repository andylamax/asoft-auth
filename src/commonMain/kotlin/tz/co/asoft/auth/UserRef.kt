package tz.co.asoft.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserRef(val uid: String, val name: String, val photoUrl: String)
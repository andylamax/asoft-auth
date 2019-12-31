package tz.co.asoft.auth

import kotlinx.serialization.Serializable

@Serializable
class App : Claimer {
    override var name = ""
    var permits = mutableListOf<String>()
    override var id: Long? = null
    override var uid = ""
    var apiKey: String? = null
}
package tz.co.asoft.auth

import kotlinx.serialization.Serializable
import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.neo4j.Neo4JEntity
import tz.co.asoft.neo4j.annotations.GeneratedValue
import tz.co.asoft.neo4j.annotations.Id
import tz.co.asoft.neo4j.annotations.NodeEntity

@Serializable
@NodeEntity
open class UserAccount : Neo4JEntity {
    @Id
    @GeneratedValue
    override var id: Long? = null
    override var uid = ""
    var name = ""
    var permits = listOf<String>()

    fun ref() = UserAccountRef().also {
        it.uid = uid
        it.name = name
    }

    companion object {
        val fake
            get() = UserAccount().apply {
                uid = ""
                name = Name.fake
            }
    }
}
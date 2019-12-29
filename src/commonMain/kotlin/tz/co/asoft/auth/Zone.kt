package tz.co.asoft.auth

import kotlinx.serialization.Serializable
import tz.co.asoft.neo4j.Neo4JEntity
import tz.co.asoft.neo4j.annotations.NodeEntity

@Serializable
@NodeEntity
class Zone : Neo4JEntity {
    override var id: Long? = null
    override var uid = ""
    var name = ""
    var level = Level.HQ.name

    enum class Level {
        Normal,
        HQ
    }
}
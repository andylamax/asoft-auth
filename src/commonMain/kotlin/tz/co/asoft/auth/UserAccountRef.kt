package tz.co.asoft.auth

import kotlinx.serialization.Serializable
import tz.co.asoft.auth.tools.name.Name
import tz.co.asoft.neo4j.Neo4JEntity
import tz.co.asoft.neo4j.annotations.GeneratedValue
import tz.co.asoft.neo4j.annotations.Id
import tz.co.asoft.neo4j.annotations.NodeEntity

@Serializable
open class UserAccountRef {
    var uid = ""
    var name = ""
}
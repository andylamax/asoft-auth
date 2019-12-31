package tz.co.asoft.auth

import tz.co.asoft.neo4j.Neo4JEntity

interface Claimer : Neo4JEntity {
    var name: String
}
package com.asofttz.auth.neo4j

actual annotation class NodeEntity actual constructor()

@Target(AnnotationTarget.FIELD)
actual annotation class Id actual constructor()

@Target(AnnotationTarget.FIELD)
actual annotation class GeneratedValue actual constructor()

@Target(AnnotationTarget.FIELD)
actual annotation class Relationship actual constructor(actual val value: String, actual val type: String, actual val direction: String)

actual annotation class Property
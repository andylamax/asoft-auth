package com.asofttz.auth.neo4j

@ExperimentalMultiplatform
@OptionalExpectation
expect annotation class ValueFor(val value: String)

expect annotation class NodeEntity()

expect annotation class Property()

@Target(AnnotationTarget.FIELD)
expect annotation class Id()

@Target(AnnotationTarget.FIELD)
expect annotation class GeneratedValue()

@Target(AnnotationTarget.FIELD)
expect annotation class Relationship constructor(val value: String, val type: String, val direction: String)

package sandbox

import hidden.Person
import kotlinx.serialization.json.Json
import tz.co.asoft.email.Email
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationTest {
    @Test
    fun should_serialize_person() {
        val p1 = Person("Anderson Lameck", Email("andy@lamax.com"))
        val json = Json.stringify(Person.serializer(), p1)
        val p2 = Json.parse(Person.serializer(), json)
        println("Json: $json")
        println("P1: $p1")
        println("P2: $p2")
        val p3 = p1.copy(name = "Anderson Lam")
        println("P3: $p3")
        assertEquals(p1, p2)
    }
}
import kotlinx.serialization.json.Json
import tz.co.asoft.auth.User
import kotlin.test.Test

class FakeUserTest {
    @Test
    fun listOfFakeUsers() {
        repeat(9) {
            println(Json.stringify(User.serializer(), User.fake))
        }
    }
}
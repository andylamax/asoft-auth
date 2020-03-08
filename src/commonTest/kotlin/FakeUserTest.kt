import kotlinx.serialization.json.Json
import tz.co.asoft.auth.User
import tz.co.asoft.auth.UserRef
import kotlin.test.Ignore
import kotlin.test.Test

class FakeUserTest {
    @Test
    fun listOfFakeUsers() {
        repeat(8) {
            println(Json.stringify(User.serializer(), User.fake))
        }
    }

    @Test
    fun canGetUserReferenceFromUser() {
        val userRef = User.fake.ref()
        println(Json.stringify(UserRef.serializer(), userRef))
    }
}
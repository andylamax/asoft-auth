import kotlinx.serialization.ImplicitReflectionSerializer
import tz.co.asoft.auth.User
import tz.co.asoft.auth.jwt.signJWT
import tz.co.asoft.auth.jwt.verifyJWT
import kotlin.test.Ignore
import kotlin.test.Test

@ExperimentalStdlibApi
class JWTTest {

    @ImplicitReflectionSerializer
    @Test
    fun prints_jwt_token() {
        val token1 = signJWT(payload = User.fake.apply { uid = "andy" }, secret = "123456")
        println("Token1: $token1")
        val verify1 = verifyJWT<User>(token1, "123456")
        println("Token1 is valid: ${verify1 != null}")

        val token2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6bnVsbCwidWlkIjoiYW5keSIsIm5hbWUiOiJMYW1lY2sgQ3lyYXgiLCJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoibGFtZWNrIiwicGVybWl0cyI6WyI6c2V0dGluZ3MiLCI6bG9ncyIsIjpwcm9maWxlIl0sInNjb3BlcyI6WyJuZXciXSwiZW1haWxzIjpbImxhbWVjay5jeXJheEBnb29nbGUuY29tIl0sInBob25lcyI6WyIyNTU3NjUxNDc5MTgiXSwicGhvdG9VcmwiOiIiLCJzdGF0dXMiOiJEZWxldGVkIiwiYWNjb3VudHMiOltdLCJ2ZXJpZmllZEVtYWlscyI6W10sInZlcmlmaWVkUGhvbmVzIjpbXSwicmVnaXN0ZXJlZE9uIjoxNTc3NzQxMDc2MTM3LCJsYXN0U2VlbiI6MTU3Nzc0MTA3NjEzN30.2EQyHq2H3aWaj0zyFBGHz-prpG7wFcUGbpAXOp2aBe8"
        println("Token2: $token2")
        val verify2 = verifyJWT<User>(token2, "123456")
        println("Token2 is valid: ${verify2 != null}")
    }
}
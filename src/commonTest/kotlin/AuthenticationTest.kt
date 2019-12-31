import di.injection
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import tz.co.asoft.auth.User
import tz.co.asoft.logging.Logger
import tz.co.asoft.test.AsyncTest
import kotlin.test.*

@Ignore
class AuthenticationTest : AsyncTest() {

    private val users = List(23) {
        User.fake
    }.groupBy { it.emails.first() }.map { it.value.first() }

    private val usersRepo get() = injection.repo.users()
    private val accountsRepo get() = injection.repo.accounts()
    private val registerUC get() = injection.useCase.registerUser()
    private val signInUC get() = injection.useCase.signIn()
    private val userStateUC get() = injection.useCase.userState()

    @BeforeTest
    fun setup_users() = asyncTest {
        users.forEach { user ->
            registerUC(user, null).catch { println(it) }
        }
    }

    @Test
    fun number_of_users_in_repo() = asyncTest {
        assertEquals(users.size, usersRepo.all().size, "Users")
    }

    @Test
    fun number_of_user_accounts_in_repo() = asyncTest {
        assertEquals(users.size, accountsRepo.all().size, "User Accounts")
    }

    @Test
    fun user_can_login() = asyncTest {
        val user = signInUC(users.random().emails.first(), "123456").respond()
        assertEquals(userStateUC.liveUser.value, user)
    }

    @Test
    fun user_can_get_token() = asyncTest {
        val user = signInUC(users.random().emails.first(), "123456").respond()
        assertEquals(userStateUC.liveUser.value, user)
    }

    @AfterTest
    fun take_down_users() = asyncTest {
        accountsRepo.wipe(users.map { it.accounts.first() })
        usersRepo.wipe(users)
    }
}
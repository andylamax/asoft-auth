import di.injection
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import tz.co.asoft.auth.User
import tz.co.asoft.test.AsyncTest
import kotlin.test.*

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
        log.i("Registering ${users.size} Users")
        users.forEach { user ->
            registerUC(user, null).catch { log.e(it) }
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
        log.i("Signing in")
        val listener = async {
            for (user in userStateUC.liveUser) {
                println("${user?.name} Logged in")
            }
        }

        val process = async {
            delay(1000)
            val user = signInUC(users.random().emails.first(), "123456")
            assertNotNull(user)
            log.i("Finishing signing in")
            user
        }

        listener.await(); process.await()
    }

    @AfterTest
    fun take_down_users() = asyncTest {
        accountsRepo.wipe(users.map { it.accounts.first() })
        usersRepo.wipe(users)
    }
}
import com.asofttz.auth.Name
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals


@JsName("NameTest2")
class `Given a name Apocalypto` {
    val name by lazy { Name("Apocalypto") }

    @Test
    @JsName("test1")
    fun `first name should be "Apocalypto"`() {
        assertEquals("Apocalypto", name.first)
    }

    @Test
    @JsName("test2")
    fun `middle name should be ""`() {
        assertEquals("", name.middle)
    }

    @Test
    @JsName("test3")
    fun `last name should be ""`() {
        assertEquals("", name.last)
    }

    @Test
    @JsName("test4")
    fun `full name should be "Apocalypto"`() {
        assertEquals("Apocalypto", name.full)
    }
}
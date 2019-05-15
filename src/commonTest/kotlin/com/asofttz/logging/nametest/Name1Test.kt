import com.asofttz.auth.Name
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

@JsName("NameTest")
class `Given a Name "Anderson Lameck Msangya"` {
    val name by lazy { Name("Anderson Lameck Msangya") }

    @Test
    @JsName("test1")
    fun `first name should be "Anderson"`() {
        assertEquals("Anderson", name.first)
    }

    @Test
    @JsName("test2")
    fun `middle name should be "Lameck"`() {
        assertEquals("Lameck", name.middle)
    }

    @Test
    @JsName("test3")
    fun `last name should be "Msangya"`() {
        assertEquals("Msangya", name.last)
    }

    @Test
    @JsName("test4")
    fun `full name should be "Anderson Lameck Msangya"`() {
        assertEquals("Anderson Lameck Msangya", name.full)
    }
}

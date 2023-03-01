import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `main test`() = assertTrue(App.main() == "example")

}

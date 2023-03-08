package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BaseIdO<T>(value: T) : BaseId<T>(value)
class BaseIdTestExample<T>(value: T) : BaseId<T>(value)

class BaseIdTest {

    @Test
    fun `should set and get BaseEntity's id property`() {
        val baseId = BaseIdO(1234)
        assertTrue(baseId.getValue() == 1234)
    }

    @Test
    fun `should expect the desired results from BaseIdO's hashCode(), toString() and equals() methods`() {

        val baseId = BaseIdO(123)
        assertEquals(baseId.toString(), "BaseId(value=123)")
        assertTrue(baseId.getValue() == 123)

        val baseIdWithZeroValue = BaseIdO(null)
        assertEquals(baseIdWithZeroValue.hashCode(), 0)

        val baseIdWithMatchingValue = BaseIdO(123)
        assertTrue(baseId == baseIdWithMatchingValue)

        val baseIdTestExample = BaseIdTestExample(123)
        assertFalse(baseId == baseIdTestExample)

        val baseIdWithMismatchingValue = BaseIdO(1243)
        assertFalse(baseId == baseIdWithMismatchingValue)

        assertTrue(baseId == baseId)
    }
}

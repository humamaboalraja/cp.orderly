package co.cp.orderly.domain.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AggregateRootO<ID> : AggregateRoot<ID>()

class AggregateRootTest {

    @Test
    fun `should check toString(), hashCode() and equals() methods in AggregateRoot`() {

        val baseEntity = AggregateRootO<Int>()
        baseEntity.setId(12345)
        assertTrue(baseEntity.getId() == 12345)
        assertEquals(baseEntity.toString(), "BaseEntity(id=12345)")
        assertTrue(baseEntity.getId() == 12345)
        assertEquals(baseEntity.hashCode(), 12345)

        val baseEntityTwo = AggregateRootO<Int>()
        baseEntityTwo.setId(12345)
        assertTrue(baseEntity == baseEntityTwo)
    }
}

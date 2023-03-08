package co.cp.orderly.domain.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Entity<ID> : BaseEntity<ID>()
class EntityMock<ID> : BaseEntity<ID>()

class BaseEntityTest {

    @Test
    fun `should set and get BaseEntity's id property`() {
        val baseEntity = Entity<Int>()
        baseEntity.setId(12345)
        assertTrue(baseEntity.getId() == 12345)
    }

    @Test
    fun `should check toString(), hashCode() and equals() methods in BaseEntity`() {
        val baseEntity = Entity<Int>()
        baseEntity.setId(12345)
        assertEquals(baseEntity.toString(), "BaseEntity(id=12345)")

        val baseEntitySecond = Entity<Int>()
        baseEntitySecond.setId(12345)
        assertEquals(baseEntity, baseEntitySecond)

        baseEntitySecond.setId(1245)
        assertFalse(baseEntity == baseEntitySecond)

        baseEntitySecond.setId(12345)
        assertTrue(baseEntity == baseEntitySecond)
        assertEquals(baseEntity.hashCode(), 12345)

        val baseEntityWithUnsetId = Entity<Int>()
        assertEquals(baseEntityWithUnsetId.hashCode(), 0)

        val otherEntity = Entity<Int>()
        otherEntity.setId(12345)
        assertTrue(baseEntity == baseEntity)

        val entityMock = EntityMock<Int>()
        entityMock.setId(1234)
        assertFalse(baseEntity == entityMock)
    }
}

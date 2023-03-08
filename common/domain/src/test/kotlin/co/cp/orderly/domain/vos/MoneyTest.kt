package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoneyMock(value: BigDecimal)

class MoneyTest {

    private val moneyInstance = Money(BigDecimal(0))

    @Test
    fun `should call and use ZERO_VALUE companion obect`() {
        val zeroValue = Money.ZERO_VALUE
        assertEquals(zeroValue, Money(BigDecimal.ZERO))
    }

    @Test
    fun `should construct money and get 'amount' property`() {
        val money = Money(BigDecimal(10))
        assertEquals(money.getAmount(), BigDecimal(10))
    }

    @Test
    fun `should construct money and get 'amount' property with setBigDecimalScale() applied `() {
        val money = Money(BigDecimal(10))
        assertEquals(money.getAmountWithDecimals(), Money(money.setBigDecimalScale(BigDecimal(10))))
    }

    @Test
    fun `should check if amount property is greater than 0`() {
        var money = Money(BigDecimal(1))
        assertTrue(money.isAmountGreaterThanZero())
        money = Money(BigDecimal(0))
        assertFalse(money.isAmountGreaterThanZero())
    }

    @Test
    fun `should check if amount property is greater than 'value'`() {
        val money = Money(BigDecimal(23))
        assertTrue(money.isAmountGreaterThan(Money(BigDecimal(22))))
        assertFalse(money.isAmountGreaterThan(Money(BigDecimal(23))))
        assertFalse(money.isAmountGreaterThan(Money(BigDecimal(24))))
    }

    @Test
    fun `should check if addMoney() is adding Money to amount from a Money object`() {
        val money = Money(moneyInstance.setBigDecimalScale(BigDecimal(23)))
        assertEquals(
            money.addMoney(Money(BigDecimal(1234))),
            Money(moneyInstance.setBigDecimalScale(BigDecimal(1257.00)))
        )

        val moneyWithUnsetBigDecimalScale = Money(BigDecimal(1257.00))
        assertNotEquals(
            money.addMoney(Money(BigDecimal(1234))),
            moneyWithUnsetBigDecimalScale
        )
    }

    @Test
    fun `should check if subtractMoney() is subtracting Money from a money object`() {
        val money = Money(moneyInstance.setBigDecimalScale(BigDecimal(1234)))
        assertEquals(
            money.subtractMoney(Money(BigDecimal(1000))),
            Money(moneyInstance.setBigDecimalScale(BigDecimal(234)))
        )

        val moneyWithUnsetBigDecimalScale = Money(BigDecimal(1257.00))
        assertNotEquals(
            money.subtractMoney(Money(BigDecimal(1000))),
            moneyWithUnsetBigDecimalScale
        )

        assertEquals(
            money.subtractMoney(Money(BigDecimal(10000))),
            Money(moneyInstance.setBigDecimalScale(BigDecimal(- 8766.00)))
        )
    }

    @Test
    fun `should check if multiplyMoney() is multiplying amount property with an input number`() {
        val money = Money(moneyInstance.setBigDecimalScale(BigDecimal(1234)))
        assertEquals(
            money.multiplyMoney(234),
            Money(moneyInstance.setBigDecimalScale(BigDecimal(288756)))
        )

        val moneyWithUnsetBigDecimalScale = Money(BigDecimal(1257))
        assertEquals(
            moneyWithUnsetBigDecimalScale.multiplyMoney(-43),
            Money(moneyInstance.setBigDecimalScale(BigDecimal(-54051.00)))
        )

        assertNotEquals(
            money.multiplyMoney(2452),
            Money(moneyInstance.setBigDecimalScale(BigDecimal(30253768.00)))
        )
    }

    @Test
    fun `Should return the correct Money entity hashCode()`() =
        assertEquals(
            Money(BigDecimal(1)).hashCode(),
            31
        )

    @Test
    fun `Should return Money's amount property converted to a string`() =
        assertEquals(
            Money(BigDecimal(12)).toString(),
            "Money(amount=12)"
        )

    @Test
    fun `should check Money entity equals() method cases`() {
        var money = Money(BigDecimal(1234))
        assertFalse(Money(BigDecimal(12234)) == money)
        assertTrue(money == money)

        val moneyWithMismatchingValue = Money(BigDecimal(12243))
        assertFalse(money == moneyWithMismatchingValue)
    }
}

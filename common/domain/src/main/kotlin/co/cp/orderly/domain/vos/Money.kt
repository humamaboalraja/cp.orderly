package co.cp.orderly.domain.vos

import java.math.BigDecimal
import java.math.RoundingMode

open class Money(private val amount: BigDecimal) {

    companion object { val ZERO_VALUE = Money(BigDecimal.ZERO) }

    fun getAmount(): BigDecimal = amount

    fun getAmountWithDecimals(): Money = Money(setBigDecimalScale(amount))

    fun isAmountGreaterThanZero(): Boolean = amount.let { amount > BigDecimal.ZERO }

    fun isAmountGreaterThan(money: Money): Boolean = amount.let { amount > money.getAmount() }

    fun addMoney(money: Money): Money = Money(setBigDecimalScale(amount.add(money.getAmount())))

    fun subtractMoney(money: Money): Money = Money(setBigDecimalScale(amount.subtract(money.getAmount())))

    fun multiplyMoney(factor: Int): Money = Money(setBigDecimalScale(amount.multiply(BigDecimal(factor))))

    fun setBigDecimalScale(value: BigDecimal): BigDecimal = value.setScale(2, RoundingMode.HALF_EVEN)

    override fun hashCode(): Int = amount.hashCode()

    override fun toString(): String = "Money(amount=$amount)"

    override fun equals(other: Any?): Boolean {
        other as Money
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            amount != other.amount -> false
            else -> true
        }
    }
}

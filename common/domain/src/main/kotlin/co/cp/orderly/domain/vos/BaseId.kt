package co.cp.orderly.domain.vos

abstract class BaseId<T> protected constructor(private val value: T) {

    fun getValue(): T = value

    override fun hashCode(): Int = value?.hashCode() ?: 0

    override fun toString(): String = "BaseId(value=$value)"

    override fun equals(other: Any?): Boolean {
        other as BaseId<*>
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            value != other.value -> false
            else -> true
        }
    }
}

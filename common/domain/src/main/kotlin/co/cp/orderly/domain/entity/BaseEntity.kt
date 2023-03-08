package co.cp.orderly.domain.entity

abstract class BaseEntity<ID> {

    private var id: ID? = null
    fun getId() = this.id
    fun setId(value: ID) { this.id = value }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    override fun toString(): String = "BaseEntity(id=$id)"

    override fun equals(other: Any?): Boolean {
        other as BaseEntity<*>
        return when {
            this === other -> true
            javaClass != other?.javaClass -> false
            id != other.id -> false
            else -> true
        }
    }
}

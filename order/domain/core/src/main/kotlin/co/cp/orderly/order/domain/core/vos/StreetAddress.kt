package co.cp.orderly.order.domain.core.vos

import java.util.Objects
import java.util.UUID

data class StreetAddress(
    val id: UUID,
    val street: String,
    val postalCode: String,
    var city: String,
) {

    override fun equals(other: Any?): Boolean {
        other as StreetAddress
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            street != other.street -> false
            postalCode != other.postalCode -> false
            city != other.city -> false
            else -> true
        }
    }

    override fun hashCode(): Int = Objects.hash(street, postalCode, city)
}

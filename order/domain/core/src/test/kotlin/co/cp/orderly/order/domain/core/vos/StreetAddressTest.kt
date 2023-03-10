package co.cp.orderly.order.domain.core.vos

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

data class ExampleStreetAddress(
    val id: UUID,
    val street: String,
    val postalCode: String,
    val city: String
)

class StreetAddressTest {

    private val streetAddress = StreetAddress(
        UUID.fromString("0ff9538f-26b5-4602-b847-1e06e970c6a9"),
        "Lichtenberg 1A",
        "2342",
        "Berlin"
    )

    @Test
    fun `should construct StreetAddress data class and check its properties validity`() {
        assertEquals(
            streetAddress.id,
            UUID.fromString("0ff9538f-26b5-4602-b847-1e06e970c6a9")
        )
        assertEquals(streetAddress.street, "Lichtenberg 1A")
        assertEquals(streetAddress.postalCode, "2342")
        assertEquals(streetAddress.city, "Berlin")
    }

    @Test
    fun `should check the validity of StreetAddress's hashCode() and toString() methods and their cases`() {

        assertEquals(
            streetAddress.toString(),
            "StreetAddress(" +
                "id=0ff9538f-26b5-4602-b847-1e06e970c6a9, " +
                "street=Lichtenberg 1A, " +
                "postalCode=2342, " +
                "city=Berlin" +
                ")"
        )

        assertEquals(streetAddress.hashCode(), -1174495393)
    }

    @Test
    fun `should check the validity of StreetAddress's equals() method and its cases`() {

        val streetAddress1stInstance = StreetAddress(
            UUID.fromString("0ff9538f-26b5-4602-b847-1e06e970c6a9"),
            "Lichtenberg 1A",
            "2342",
            "Berlin"
        )

        val streetAddress2ndInstance = StreetAddress(
            UUID.fromString("0ff9538f-26b5-4602-b847-1e06e970c6a9"),
            "Lichtenberg 11A", "9232", "berlin 2"
        )

        val streetAddress3rdInstance = StreetAddress(
            UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
            "Example street 11A", "661", "City 1"
        )

        assertTrue(streetAddress == streetAddress1stInstance)
        assertTrue(streetAddress == streetAddress)
        assertFalse(streetAddress2ndInstance == streetAddress3rdInstance)

        streetAddress2ndInstance.city = "Hamburg"
        streetAddress3rdInstance.city = "San Fransisco"
        assertFalse(streetAddress2ndInstance.city.equals(streetAddress3rdInstance.city))

        Assertions.assertNotEquals(
            StreetAddress(
                UUID.fromString("9df9538f-26b5-4602-b847-1e06e970c6a9"),
                "Example street 11A", "3661", "San Fransisco"
            ),
            StreetAddress(
                UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
                "Example street 11A", "4361", "Berlin"
            )
        )

        assertFalse(
            StreetAddress(
                UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
                "Example street 11A", "3661", "San Fransisco"
            ) ==
                StreetAddress(
                    UUID.fromString("9df9538f-26b5-4602-b847-1e06e970c6a9"),
                    "Example sdtreet 11A", "36361", "San Franssisco"
                )
        )

        assertFalse(
            StreetAddress(
                UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
                "Example street 11A", "3661", "San"
            ) ==
                StreetAddress(
                    UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
                    "Example street 11A", "3661", "San Fransisco"
                )
        )

        assertFalse(
            StreetAddress(
                UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
                "Example street 11A", "3661", "San"
            ).javaClass ==
                ExampleStreetAddress(
                    UUID.fromString("9ff9538f-26b5-4602-b847-1e06e970c6a9"),
                    "Example street 11A", "3661", "San Fransisco"
                ).javaClass
        )

        assertTrue(streetAddress3rdInstance.javaClass == streetAddress.javaClass)
    }
}

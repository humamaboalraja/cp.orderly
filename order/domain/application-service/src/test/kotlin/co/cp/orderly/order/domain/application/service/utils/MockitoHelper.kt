package co.cp.orderly.order.domain.application.service.utils

import org.mockito.Mockito

object MockitoHelper {
    fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}

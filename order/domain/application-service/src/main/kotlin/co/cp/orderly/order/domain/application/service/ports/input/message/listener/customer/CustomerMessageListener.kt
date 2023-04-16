package co.cp.orderly.order.domain.application.service.ports.input.message.listener.customer

import co.cp.orderly.order.domain.application.service.dto.message.customer.CustomerModelDTO

interface CustomerMessageListener {
    fun customerCreated(customerModel: CustomerModelDTO)
}

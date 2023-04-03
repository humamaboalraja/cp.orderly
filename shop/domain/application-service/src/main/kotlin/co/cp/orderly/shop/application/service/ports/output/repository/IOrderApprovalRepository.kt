package co.cp.orderly.shop.application.service.ports.output.repository

import co.cp.orderly.shop.domain.core.entity.OrderApproval

interface IOrderApprovalRepository {
    fun save(orderApproval: OrderApproval): OrderApproval
}

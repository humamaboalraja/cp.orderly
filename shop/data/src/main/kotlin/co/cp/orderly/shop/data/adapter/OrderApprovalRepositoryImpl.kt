package co.cp.orderly.shop.data.adapter

import co.cp.orderly.shop.application.service.mapper.ShopApplicationServiceDataMapper
import co.cp.orderly.shop.application.service.ports.output.repository.IOrderApprovalRepository
import co.cp.orderly.shop.data.mapper.ShopDataLayerDataMapper
import co.cp.orderly.shop.data.repository.OrderApprovalPersistenceRepository
import co.cp.orderly.shop.domain.core.entity.OrderApproval

class OrderApprovalRepositoryImpl(
    val orderApprovalPersistenceRepository: OrderApprovalPersistenceRepository,
    val shopApplicationServiceDataMapper: ShopDataLayerDataMapper
) : IOrderApprovalRepository {
    override fun save(orderApproval: OrderApproval): OrderApproval =
        shopApplicationServiceDataMapper.convertOrderApprovalEntityToOrderApproval(
            orderApprovalPersistenceRepository.save(
                shopApplicationServiceDataMapper.orderApprovalToOrderApprovalEntity(orderApproval)))
}

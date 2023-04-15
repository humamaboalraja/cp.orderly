package co.cp.orderly.order.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["co.cp.orderly.order.data", "co.cp.orderly.common.data"])
@EntityScan(basePackages = ["co.cp.orderly.order.data", "co.cp.orderly.common.data"])
@SpringBootApplication(
    scanBasePackages = ["co.cp.orderly"],
    exclude = [
        (DataSourceAutoConfiguration::class),
        (DataSourceTransactionManagerAutoConfiguration::class),
        (HibernateJpaAutoConfiguration::class)
    ]
)
class OrderService
fun main(args: Array<String>) {
    try { SpringApplication.run(OrderService::class.java, *args) } catch (ex: Exception) { throw ex }
}

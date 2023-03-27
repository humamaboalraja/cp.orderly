package co.cp.orderly.order.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["co.cp.orderly.order.data"])
@EntityScan(basePackages = ["co.cp.orderly.order.data"])
@SpringBootApplication(scanBasePackages = ["co.cp.orderly"], exclude = [HibernateJpaAutoConfiguration::class])
class OrderService
fun main(args: Array<String>) {
    try {
        SpringApplication.run(OrderService::class.java, *args)
    } catch (ex: Exception){
        println(ex)
    }
}


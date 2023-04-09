package co.cp.orderly.customer.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
@EnableJpaRepositories(basePackages = ["co.cp.orderly.customer.data", "co.cp.orderly.common.data"])
@EntityScan(basePackages = [ "co.cp.orderly.customer.data", "co.cp.orderly.common.data" ])
@SpringBootApplication(scanBasePackages = ["co.cp.orderly"])
class CustomerServiceApp

fun main(args: Array<String>) {
    try {
        SpringApplication.run(CustomerServiceApp::class.java, *args)
    } catch (exception: Exception) {
        throw exception
    }
}

package co.cp.orderly.shop.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["co.cp.orderly.shop.data", "co.cp.orderly.common.data"])
@EntityScan(basePackages = ["co.cp.orderly.shop.data", "co.cp.orderly.common.data"])
@SpringBootApplication(scanBasePackages = ["co.cp.orderly"])
open class ShopServiceApp

fun main(args: Array<String>) {
    SpringApplication.run(ShopServiceApp::class.java, *args)
}

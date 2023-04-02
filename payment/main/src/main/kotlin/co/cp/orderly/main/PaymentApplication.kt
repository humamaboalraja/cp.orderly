package co.cp.orderly.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["co.cp.orderly.payment.data"])
@EntityScan(basePackages = ["co.cp.orderly.payment.data"])
@SpringBootApplication(scanBasePackages = ["co.cp.orderly"])
open class PaymentApplication

fun main(args: Array<String>) {
    SpringApplication.run(PaymentApplication::class.java, *args)
}

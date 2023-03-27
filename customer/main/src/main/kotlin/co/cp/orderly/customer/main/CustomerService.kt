package co.cp.orderly.customer.main

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["co.cp.orderly"])
class CustomerService

fun main(args: Array<String>) {
    try {
        SpringApplication.run(CustomerService::class.java, *args)
    } catch (exception: Exception) {
        throw exception
    }
}

package com.khun.webservice.kotlinspringboot.model

import jakarta.persistence.*
import java.time.LocalDate
import kotlin.random.Random

@Entity
data class Customer(
    @Id
    @GeneratedValue
    var id: Long = 0,
    val firstName: String,
    val lastName: String,
    val dob: LocalDate,
    val email: String,
    @OneToOne
    val address: Address,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    val accounts: Set<Account> = emptySet()
) {
    val customerId: Long = createCustomerId()
    private fun createCustomerId() = Random.nextLong(10000, 99999)

    private val nameRegex = Regex("[A-Za-z]+")

    init {
        require(firstName.matches(nameRegex)) {
            "Firstname ${firstName} contain special characters!"
        }

        require(lastName.matches(nameRegex)) {
            "Lastname ${lastName} contains special characters!"
        }

        val now = LocalDate.now()
        require(dob.isBefore(now.minusYears(18)) && dob.isAfter(now.minusYears(100))) {
            "Age must be between 18 and 100!"
        }
    }
}

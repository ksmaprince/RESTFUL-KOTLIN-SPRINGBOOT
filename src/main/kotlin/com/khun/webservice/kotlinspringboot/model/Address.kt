package com.khun.webservice.kotlinspringboot.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
data class Address(
    @Id
    @GeneratedValue
    var id: Long = 0,
    val street: String,
    val number: String,
    val zipCode: Int,
    val city: String,
    val country: String
) {
    init {
        require(zipCode in 10000..99999) {
            "Zip Code must contain 5 digits!"
        }
        require(Locale.getISOCountries().contains(country)) {
            "Country $country must be ISO conform!"
        }
    }
}

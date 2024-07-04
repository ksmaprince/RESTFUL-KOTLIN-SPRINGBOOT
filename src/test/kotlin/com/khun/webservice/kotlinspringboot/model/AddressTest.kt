package com.khun.webservice.kotlinspringboot.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AddressTest {

    @Test
    fun `create Address fail if zipCode is too short`() {
        Assertions.assertThatThrownBy {
            Address(
                street = "Main Street",
                number = "123",
                zipCode = 123,
                city = "Los Angeles",
                country = "US"
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Zip Code must contain 5 digits!")
    }

    @Test
    fun `create Address fail if zipCode is too long`() {
        Assertions.assertThatThrownBy {
            Address(
                street = "Main Street",
                number = "123",
                zipCode = 12345678,
                city = "Los Angeles",
                country = "US"
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Zip Code must contain 5 digits!")
    }

    @Test
    fun `create Address fail if country is invalid`() {
        Assertions.assertThatThrownBy {
            Address(
                street = "Main Street",
                number = "123",
                zipCode = 12345,
                city = "Los Angeles",
                country = "XXX"
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Country XXX must be ISO conform!")
    }
}
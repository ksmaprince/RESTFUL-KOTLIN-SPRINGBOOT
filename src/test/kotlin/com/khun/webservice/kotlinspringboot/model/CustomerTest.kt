package com.khun.webservice.kotlinspringboot.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CustomerTest {

    @Test
    fun `create customer return 5 digits id`() {
        val customer = Customer(
            firstName = "Khun",
            lastName = "Aung",
            dob = LocalDate.of(1999, 1, 2),
            email = "khunaung@gmail.com",
            address = Address(
                street = "Main Street",
                number = "13",
                zipCode = 90001,
                city = "Clevaland",
                country = "US"
            ),
            accounts = emptySet()
        )
        val actual = customer.customerId
        assertThat(actual).isBetween(10000, 99999)
    }

    @Test
    fun `create customerId is random`() {
        val customer1 = Customer(
            firstName = "Khun",
            lastName = "Aung",
            dob = LocalDate.of(1999, 1, 2),
            email = "khunaung@gmail.com",
            address = Address(
                street = "Main Street",
                number = "13",
                zipCode = 90001,
                city = "Clevaland",
                country = "US"
            ),
            accounts = emptySet()
        )

        val customer2 = Customer(
            firstName = "Noah",
            lastName = "Nguyen",
            dob = LocalDate.of(1999, 3, 5),
            email = "noah.nguyen@gmail.com",
            address = Address(
                street = "Main Street",
                number = "13",
                zipCode = 90001,
                city = "Clevaland",
                country = "US"
            ),
            accounts = emptySet()
        )

        val actual1 = customer1.customerId
        val actual2 = customer2.customerId

        assertThat(actual1).isNotEqualTo(actual2)
    }

    @Test
    fun `create customer fail if firstname contains special characters`(){
        val firstName = "Khun#"
        assertThatThrownBy {
            Customer(
                firstName = firstName,
                lastName = "Aung",
                dob = LocalDate.of(1999, 1, 2),
                email = "khunaung@gmail.com",
                address = Address(
                    street = "Main Street",
                    number = "13",
                    zipCode = 90001,
                    city = "Clevaland",
                    country = "US"
                ),
                accounts = emptySet()
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Firstname ${firstName} contain special characters!")
    }

    @Test
    fun `create customer fail if lastname contains special characters`(){
        val lastname = "Aung#"
        assertThatThrownBy {
            Customer(
                firstName = "Khun",
                lastName = lastname,
                dob = LocalDate.of(1999, 1, 2),
                email = "khunaung@gmail.com",
                address = Address(
                    street = "Main Street",
                    number = "13",
                    zipCode = 90001,
                    city = "Clevaland",
                    country = "US"
                ),
                accounts = emptySet()
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Lastname ${lastname} contains special characters!")
    }

    @Test
    fun `create customer fail if age is less than 18 years`(){
        assertThatThrownBy {
            Customer(
                firstName = "Khun",
                lastName = "Aung",
                dob = LocalDate.of(2019, 1, 2),
                email = "khunaung@gmail.com",
                address = Address(
                    street = "Main Street",
                    number = "13",
                    zipCode = 90001,
                    city = "Clevaland",
                    country = "US"
                ),
                accounts = emptySet()
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Age must be between 18 and 100!")
    }

    @Test
    fun `create customer fail if age is greater than 100 years`(){
        assertThatThrownBy {
            Customer(
                firstName = "Khun",
                lastName = "Aung",
                dob = LocalDate.of(1900, 1, 2),
                email = "khunaung@gmail.com",
                address = Address(
                    street = "Main Street",
                    number = "13",
                    zipCode = 90001,
                    city = "Clevaland",
                    country = "US"
                ),
                accounts = emptySet()
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Age must be between 18 and 100!")
    }

}
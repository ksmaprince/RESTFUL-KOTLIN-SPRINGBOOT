package com.khun.webservice.kotlinspringboot.repository

import com.khun.webservice.kotlinspringboot.data.RealDatabaseTest
import com.khun.webservice.kotlinspringboot.model.Address
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RealDatabaseTest
class AddressRepositoryTest {

    @Autowired
    private lateinit var addressRepository: AddressRepository

    @Test
    fun `save address is possible`(){
        val address = Address(
            street = "100 8TH STREET",
            number = "203",
            zipCode = 90001,
            city = "Cleveland",
            country = "US"
        )

        val actual = addressRepository.save(address)

        assertThat(addressRepository.findById(actual.id).get()).isEqualTo(address)
    }
}
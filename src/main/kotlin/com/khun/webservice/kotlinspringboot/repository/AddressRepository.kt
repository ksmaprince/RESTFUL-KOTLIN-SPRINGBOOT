package com.khun.webservice.kotlinspringboot.repository

import com.khun.webservice.kotlinspringboot.model.Address
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface AddressRepository : CrudRepository<Address, Long> {
    fun findAddressByZipCode(zipCode: Int): Optional<Address>
}
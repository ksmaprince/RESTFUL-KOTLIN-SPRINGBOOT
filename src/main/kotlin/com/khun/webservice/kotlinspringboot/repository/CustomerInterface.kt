package com.khun.webservice.kotlinspringboot.repository

import com.khun.webservice.kotlinspringboot.model.Customer
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface CustomerInterface {
    fun findByFirstNameAndLastName(firstName: String, lastName:String): Optional<Customer>
    fun existsCustomerByEmail(email:String): Boolean
}
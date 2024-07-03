package com.khun.webservice.kotlinspringboot.repository

import com.khun.webservice.kotlinspringboot.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Repository
@Transactional(propagation = Propagation.REQUIRED)
interface CustomerRepository : CrudRepository<Customer, Long>{
    fun findByFirstNameAndLastName(firstName: String, lastName:String): Optional<Customer>
    fun existsCustomerByEmail(email:String): Boolean
}
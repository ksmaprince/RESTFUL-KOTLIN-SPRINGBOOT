package com.khun.webservice.kotlinspringboot.service

import com.khun.webservice.kotlinspringboot.dto.CustomerDto
import com.khun.webservice.kotlinspringboot.model.Account
import com.khun.webservice.kotlinspringboot.model.Address
import com.khun.webservice.kotlinspringboot.model.Customer
import com.khun.webservice.kotlinspringboot.repository.AddressRepository
import com.khun.webservice.kotlinspringboot.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class CustomerService(private val customerRepository: CustomerRepository, private val addressRepository: AddressRepository) {

    @Transactional
    fun addNewCustomer(customerDto: CustomerDto): ApiResult<Any>{
        try {
            val customer = mapCustomerDtoToCustomer(customerDto)

            if (customerRepository.existsCustomerByEmail(customer.email))
                return ApiResult.Fail(ErrorCode.DUPLICATE_EMAIL, "The email ${customer.email} is already exists")

            val address = addressRepository.findAddressByZipCode(customer.address.zipCode)
            val customerToPersist = if (address.isEmpty){
                customer.copy(address = addressRepository.save(customer.address))
            }else{
                customer.copy(address = address.get())
            }
            return ApiResult.Success(customerRepository.save(customerToPersist))
        }catch (e: DateTimeParseException){
            return ApiResult.Fail(
                ErrorCode.INVALID_DATE,
                "The birthdate ${customerDto.birthdate} is not expected format (MM/dd/yyyy)"
            )
        }catch (e: Exception){
            return ApiResult.Fail(ErrorCode.GENERAL_ERROR, "Unexpected Error occur: ${e.message}")
        }
    }

    fun getAllCustomer(): ApiResult<Any>{
        try {
            return ApiResult.Success(customerRepository.findAll())
        }catch (e: Exception){
            return ApiResult.Fail(ErrorCode.GENERAL_ERROR, "Unexpected Error occur: ${e.message}")
        }
    }

    private fun mapCustomerDtoToCustomer(customerDto: CustomerDto): Customer{
        return Customer(
            firstName = customerDto.firstName,
            lastName = customerDto.lastName,
            dob = LocalDate.parse(customerDto.birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
            email = customerDto.email,
            address = Address(
                street = customerDto.address.street,
                number = customerDto.address.number,
                zipCode = customerDto.address.zipCode,
                city = customerDto.address.city,
                country = customerDto.address.country
            ),
            accounts = customerDto.accounts.map {
                Account(
                    number = it.number,
                    balance = it.balance
                )
            }.toSet()
        )
    }
}

sealed class ApiResult<out T>{
    internal data class Fail(val errorCode: ErrorCode, val errorMessage: String) : ApiResult<Nothing>()
    internal data class Success<T>(val value: T): ApiResult<T>()
}

enum class ErrorCode{
    DUPLICATE_EMAIL,
    INVALID_DATE,
    GENERAL_ERROR
}
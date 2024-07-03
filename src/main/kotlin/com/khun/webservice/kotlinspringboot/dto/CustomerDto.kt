package com.khun.webservice.kotlinspringboot.dto

data class CustomerDto(
    val firstName: String,
    val lastName: String,
    val birthdate: String,
    val email: String,
    val address: AddressDto,
    val accounts: Set<AccountDto>
)
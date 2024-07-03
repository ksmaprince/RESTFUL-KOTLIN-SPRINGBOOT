package com.khun.webservice.kotlinspringboot.dto

data class AddressDto(
    val street: String,
    val number: String,
    val city: String,
    val zipCode: Int,
    val country: String
)

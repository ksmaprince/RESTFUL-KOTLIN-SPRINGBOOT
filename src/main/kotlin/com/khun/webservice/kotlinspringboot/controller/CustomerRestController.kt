package com.khun.webservice.kotlinspringboot.controller

import com.khun.webservice.kotlinspringboot.dto.CustomerDto
import com.khun.webservice.kotlinspringboot.service.ApiResult
import com.khun.webservice.kotlinspringboot.service.CustomerService
import com.khun.webservice.kotlinspringboot.service.ErrorCode
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerRestController(private val customerService: CustomerService) {

    @PostMapping("/api/v1/customer")
    fun addCustomer(@RequestBody customerDto: CustomerDto): ResponseEntity<out Any> {
        return customerService.addNewCustomer(customerDto).let { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    handleSuccess(result = apiResult)
                }

                is ApiResult.Fail -> {
                    handleFailure(result = apiResult)
                }
            }
        }
    }

    @GetMapping("/api/v1/customers")
    fun getAllCustomer(): ResponseEntity<out Any>{
        return customerService.getAllCustomer().let { apiResult ->
            when(apiResult){
                is ApiResult.Success -> {
                    ResponseEntity.status(200).body(apiResult.value)
                }

                is ApiResult.Fail -> {
                    ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorDto(apiResult.errorCode.toString(), apiResult.errorMessage))
                }
            }
        }
    }

    private fun handleSuccess(result: ApiResult.Success<Any>): ResponseEntity<SuccessDto> {
        return ResponseEntity.status(CREATED).body(SuccessDto(result.value))
    }

    private fun handleFailure(result: ApiResult.Fail): ResponseEntity<ErrorDto> {
        val code = result.errorCode
        val status = when (code) {
            ErrorCode.INVALID_DATE,
            ErrorCode.DUPLICATE_EMAIL -> BAD_REQUEST

            ErrorCode.GENERAL_ERROR -> INTERNAL_SERVER_ERROR
        }
        val errorDto = ErrorDto(code.name, result.errorMessage)
        return ResponseEntity.status(status).body(errorDto)
    }
}

data class ErrorDto(val errorCode: String, val errorMessage: String)

data class SuccessDto(val customerId: Any)
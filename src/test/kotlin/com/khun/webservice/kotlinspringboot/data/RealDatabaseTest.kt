package com.khun.webservice.kotlinspringboot.data

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(CleanDatabaseDataSourceExtension::class)
@SpringBootTest
@ContextConfiguration(classes = [TestContextDataSourceConfiguration::class])
annotation class RealDatabaseTest
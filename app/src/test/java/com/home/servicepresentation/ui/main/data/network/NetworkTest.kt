package com.home.servicepresentation.ui.main.data.network

import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class NetworkTest {

    private val trueUrl = "https://api-dot-rafiji-staging.appspot.com/customer/v2/home"
    private val falseUrl = "https://api-dot-rafiji-staging.appspot.com/customer/v2/false"
    private lateinit var network: Network

    @Before
    fun setup(){
        network = Network()
    }

    @Test
    fun getApiDataOf_Not_Null_With_True_Url() {

        val result = network.getApiDataOf(trueUrl)
        assertNotNull(result)

    }

    @Test
    fun getApiDataOf_Not_Null_With_Fals_Url() {

        val result = network.getApiDataOf(falseUrl)
        assertNotNull(result)

    }

    @Test
    fun getApiDataOf_Not_Null_With_Null_Url() {

        val result = network.getApiDataOf("")
        assertNotNull(result)

    }
}
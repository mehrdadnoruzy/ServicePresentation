package com.home.servicepresentation.data.network

import com.google.gson.GsonBuilder
import com.home.servicepresentation.data.SingletonHolder
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.models.home.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Network private constructor(private val checkNetwork: CheckNetwork) {

    companion object : SingletonHolder<Network, CheckNetwork>(::Network)

    private val ERROR = "ERROR"

    private val baseUrl = "https://api-dot-rafiji-staging.appspot.com/customer/v2/"

    suspend fun getHomeData(): BaseModel<HomeModel>? {
        return withContext(Dispatchers.Default) {
            createHomeModel(callApi() {
                getApiDataOf("${baseUrl}home")
            })
        }
    }

    suspend fun getDetailData(): BaseModel<DetailModel>? {
        return withContext(Dispatchers.Default) {
            createDetailModel(callApi() {
                getApiDataOf("${baseUrl}categories/carwash/services")
            })
        }
    }

    private fun callApi(apiCall: () -> String): String {
        return if (checkNetwork.checkConnectivity()) apiCall.invoke()
        else "$ERROR:600:Check your internet connection and try again"
    }

    fun getApiDataOf(url: String): String {
        try {
            val httpClient = openConnection(url)
            return if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    readStream(inputStream = BufferedInputStream(httpClient.inputStream))
                } catch (e: Exception) {
                    "$ERROR:700:${e.message}"
                } finally {
                    httpClient.disconnect()
                }
            } else "$ERROR:${httpClient.responseCode}:${httpClient.responseMessage}"
        } catch (e: Exception) {
            return "$ERROR:800:${e.message}"
        }
    }

    fun openConnection(url: String): HttpURLConnection {
        val httpClient = URL(url).openConnection() as HttpURLConnection
        httpClient.connectTimeout = 5000
        httpClient.readTimeout = 5000
        return httpClient
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun createHomeModel(data: String): BaseModel<HomeModel>? {
        return if (!data.startsWith(ERROR))
            BaseModel(
                code = "200",
                msg = "ok",
                data = GsonBuilder().create().fromJson(data, HomeModel::class.java)
            )
        else BaseModel(
            code = data.split(":")[1],
            msg = data.split(":")[2],
            data = null
        )
    }

    fun createDetailModel(data: String): BaseModel<DetailModel>? {
        return if (!data.startsWith(ERROR))
            BaseModel(
                code = "200",
                msg = "ok",
                data = GsonBuilder().create().fromJson(data, DetailModel::class.java)
            )
        else BaseModel(
            code = data.split(":")[1],
            msg = data.split(":")[2],
            data = null
        )
    }

    //return readStream(inputStream = BufferedInputStream(httpClient.errorStream))
    private fun createErrorBodyModel(data: String): BaseModel<*>? {
        val jObject = JSONObject(data)
        val status = jObject.getString("status")
        val message = jObject.getString("message")

        return BaseModel(
            code = status,
            msg = message,
            data = null
        )
    }
}

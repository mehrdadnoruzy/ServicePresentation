package com.home.servicepresentation.ui.main.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.GsonBuilder
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL

class Network() {

    private val ERROR = "ERROR"

    private val baseUrl = "https://api-dot-rafiji-staging.appspot.com/customer/v2/"

    suspend fun getHomeData(context: Context): BaseModel<HomeModel>? {
        return withContext(Dispatchers.Default) {
            createHomeModel(callApi(context) {
                getApiDataOf("${baseUrl}home")
            })
        }
    }

    suspend fun getDetailData(context: Context): BaseModel<DetailModel>? {
        return withContext(Dispatchers.Default) {
            createDetailModel(callApi(context) {
                getApiDataOf("${baseUrl}categories/carwash/services")
            })
        }
    }

    private fun callApi(context: Context, apiCall: () -> String): String {
        return if (checkConnectivity(context)) apiCall.invoke()
            else "$ERROR:Check your internet connection and try again.:600"
    }

    private fun getApiDataOf(url: String): String {
        try {
            val httpClient = URL(url).openConnection() as HttpURLConnection
            httpClient.connectTimeout = 5000
            httpClient.readTimeout = 5000
            return if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    readStream(inputStream = BufferedInputStream(httpClient.inputStream))
                } catch (e: Exception) {
                    ERROR + ":" + e.message + ":" + "700"
                } finally {
                    httpClient.disconnect()
                }
            } else ERROR + ":" + httpClient.responseMessage + ":" + httpClient.responseCode
        } catch (e: Exception) {
            return ERROR + ":" + e.message + ":" + "800"
        }
    }

    private fun readStream(inputStream: BufferedInputStream): String {
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    private fun createHomeModel(data: String): BaseModel<HomeModel>? {
        return if (!data.startsWith(ERROR))
            BaseModel(
                code = "200",
                msg = "ok",
                data = GsonBuilder().create().fromJson(data, HomeModel::class.java)
            )
        else BaseModel(
            code = data.split(":")[2],
            msg = data.split(":")[1],
            data = null
        )
    }

    private fun createDetailModel(data: String): BaseModel<DetailModel>? {
        return if (!data.startsWith(ERROR))
            BaseModel(
                code = "200",
                msg = "ok",
                data = GsonBuilder().create().fromJson(data, DetailModel::class.java)
            )
        else BaseModel(
            code = data.split(":")[2],
            msg = data.split(":")[1],
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

    private fun checkConnectivity(context: Context): Boolean {
        return (isConnectionOn(context) && isInternetAvailable())
    }

    private fun isConnectionOn(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val connection = connectivityManager.getNetworkCapabilities(network)
            return connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
            }
            return false
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }
}

package com.home.servicepresentation.ui.main.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.GsonBuilder
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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


    fun getHomeData(context: Context): Deferred<BaseModel<HomeModel>?> {

        return GlobalScope.async {
            var result = callApi(context) {
                getDataApi("${baseUrl}home")
            }
            createHomeModel(result)
        }
    }

    fun getDetailData(context: Context): Deferred<BaseModel<DetailModel>?> {

        return GlobalScope.async {
            var result = callApi(context) {
                getDataApi("${baseUrl}categories/carwash/services")
            }
            createDetailModel(result)
        }
    }


    fun callApi(context: Context, apiCall: () -> String): String {
        if (checkConnectivity(context)) {
            return apiCall.invoke()
        } else return ERROR+":"+"Check your internet connection and try again."+":"+"600"
    }

    fun getDataApi(url: String): String {

        try {
            val url = URL(url)
            val httpClient = url.openConnection() as HttpURLConnection
            httpClient.connectTimeout = 5000
            httpClient.readTimeout = 5000
            if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    val stream = BufferedInputStream(httpClient.inputStream)
                    val data: String = readStream(inputStream = stream)
                    return data
                } catch (e: Exception) {
                    return ERROR+":"+ e.message +":"+"700"
                } finally {
                    httpClient.disconnect()
                }
            } else return ERROR+":"+ httpClient.responseMessage + ":" + httpClient.responseCode

        } catch (e: Exception) {
            return ERROR+":"+ e.message +":"+"800"
        }
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun createHomeModel(data: String): BaseModel<HomeModel>? {
        if (!data.startsWith(ERROR))
            return BaseModel(
                code = "200",
                msg = "ok",
                data = GsonBuilder().create().fromJson(data, HomeModel::class.java)
            )
        else return BaseModel(
            code = data?.split(":")?.get(2),
            msg = data?.split(":")?.get(1),
            data = null
        )
    }

    fun createDetailModel(data: String): BaseModel<DetailModel>? {
        if (!data.startsWith(ERROR))
            return BaseModel(
                code = "200",
                msg = "ok",
                data = GsonBuilder().create().fromJson(data, DetailModel::class.java)
            )
        else return BaseModel(
            code = data?.split(":")?.get(2),
            msg = data?.split(":")?.get(1),
            data = null
        )
    }

    fun checkConnectivity(context: Context): Boolean {
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

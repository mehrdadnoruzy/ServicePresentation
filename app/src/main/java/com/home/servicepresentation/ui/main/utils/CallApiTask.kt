package com.home.servicepresentation.ui.main.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import com.google.gson.GsonBuilder
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL
@Deprecated("Operation of this class was slow")
class CallApiTask(private val context: Context, private val apiType: String) :
    AsyncTask<String, Unit, BaseModel<*>?>() {

    override fun doInBackground(vararg url: String?): BaseModel<*>? {
        if (check(context)) {
            val url = URL(url[0])
            val httpClient = url.openConnection() as HttpURLConnection
            httpClient.connectTimeout = 5000
            httpClient.readTimeout = 5000
            if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    val stream = BufferedInputStream(httpClient.inputStream)
                    val data: String = readStream(inputStream = stream)
                    return createModel(data, apiType)
                } catch (e: Exception) {
                    return BaseModel(
                        msg = e.message,
                        data = null
                    )
                } finally {
                    httpClient.disconnect()
                }
            } else {
                return BaseModel(
                    code = httpClient.responseCode.toString(),
                    msg = httpClient.responseMessage,
                    data = null
                )
            }
            return null
        } else return BaseModel(
            msg = "Check your internet connection and try again.",
            data = null
        )
    }

    fun createModel(data: String, apiType: String): BaseModel<*>? {
        when (apiType) {
            "home" -> return BaseModel(
                code = null,
                msg = null,
                data = GsonBuilder().create().fromJson(data, HomeModel::class.java)
            )
            "detail" -> return BaseModel(
                code = null,
                msg = null,
                data = GsonBuilder().create().fromJson(data, DetailModel::class.java)
            )
            else -> return null
        }
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun check(context: Context): Boolean {
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
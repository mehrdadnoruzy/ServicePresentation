package com.home.servicepresentation.ui.main.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import com.google.gson.GsonBuilder
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.base.BaseObservable
import com.home.servicepresentation.ui.main.data.models.detail.CarwashModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import java.io.*
import java.net.*


class Network() {

    private val baseUrl = "https://api-dot-rafiji-staging.appspot.com/customer/v2/"
    var baseObservable =
        BaseObservable()

    fun getHomeData(context: Context){
        var result = GetHomeTask(context).execute(baseUrl+"home").get()
        if (result!=null)
            baseObservable.addModel(result)
         else
            baseObservable.addModel(
                BaseModel(
                    msg = "پاسخی از سرور دریافت نشد",
                    data = null
                )
            )
    }

    class GetHomeTask(private val context: Context) : AsyncTask<String, Unit, BaseModel<HomeModel>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            //check
        }

        override fun doInBackground(vararg url: String?): BaseModel<HomeModel>? {
            if (check(context)) {
                val url = URL(url[0])
                val httpClient = url.openConnection() as HttpURLConnection
                if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                    try {
                        val stream = BufferedInputStream(httpClient.inputStream)
                        val data: String = readStream(inputStream = stream)
                        return createModel(data)
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
                        code = httpClient.responseCode,
                        msg = httpClient.responseMessage,
                        data = null
                    )
                }
                return null
            } else return BaseModel(
                msg = "اتصال به اینترنت را بررسی کنید و مجدد تلاش کنید",
                data = null
            )
        }

        fun createModel(data: String): BaseModel<HomeModel> {
            return BaseModel(
                code = null,
                msg = null,
                data = GsonBuilder().create().fromJson(data, HomeModel::class.java)
            )
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
        override fun onPostExecute(result: BaseModel<HomeModel>?) {
            super.onPostExecute(result)
        }
    }

    fun getCarwashData(){
        GetCarwashTask().execute("\"$baseUrl/categories/carwash/services\"")
    }

    class GetCarwashTask() : AsyncTask<String, Unit, String>() {

        override fun doInBackground(vararg url: String?): String? {
            val url = URL(url[0])
            val httpClient = url.openConnection() as HttpURLConnection
            if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    val stream = BufferedInputStream(httpClient.inputStream)
                    val data: String = readStream(inputStream = stream)
                    return data
                } catch (e: Exception) {
/*                    viewModel.showError(
                        ErrorModel(
                            msg = e.message
                        )
                    )*/
                } finally {
                    httpClient.disconnect()
                }
            } else {
/*                viewModel.showError(
                    ErrorModel(
                        code = httpClient.responseCode
                    )
                )*/
            }
            return null
        }

        fun readStream(inputStream: BufferedInputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            bufferedReader.forEachLine { stringBuilder.append(it) }
            return stringBuilder.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            result?.let {
                var carwashModel: CarwashModel  =
                    GsonBuilder().create().fromJson(result, CarwashModel::class.java)
                //viewModel.setHomeResponse(homeModel)
            }
        }
    }

/*    private fun isConnectionOn(context: Context): Boolean {
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
    }*/

/*    class NoConnectivityException : IOException() {
        override val message: String
            get() = "اتصال به اینترنت را بررسی کنید و مجدد تلاش کنید"
    }

    class NoInternetException() : IOException() {
        override val message: String
            get() = "اتصال به اینترنت را بررسی کنید و مجدد تلاش کنید"
    }

    class TimeoutException() : SocketTimeoutException() {
        override val message: String
            get() = "پاسخی از سرور دریافت نشد"
    }*/

/*    fun httpGet(myURL: String?): String {

        val inputStream:InputStream
        val result:String

        // create URL
        val url:URL = URL(myURL)

        // create HttpURLConnection
        val conn:HttpURLConnection = url.openConnection() as HttpURLConnection

        // make GET request to the given URL
        conn.connect()

        // receive response as inputStream
        inputStream = conn.inputStream

        // convert inputstream to string
        if(inputStream != null) {
            result = convertInputStreamToString(inputStream)
            var homeModel = JSONObject(result)//.toString()
        }
        else
            result = "Did not work!"

        return result
    }

    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader:BufferedReader? = BufferedReader(InputStreamReader(inputStream))

        var line:String? = bufferedReader?.readLine()
        var result:String = ""

        while (line != null) {
            result += line
            line = bufferedReader?.readLine()
        }

        inputStream.close()
        return result
    }*/
}
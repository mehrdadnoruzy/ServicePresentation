package com.home.servicepresentation.ui.main.presentation.activities.main

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.domain.DetailUsecase
import com.home.servicepresentation.ui.main.domain.HomeUsecase
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MyViewModel(private val homeUsecase: HomeUsecase,
                  private val detailUsecase: DetailUsecase
): ViewModel() {
    var liveData: MutableLiveData<BaseModel<HomeModel>?> = MutableLiveData()
    fun getHomeData(context: Context) {

        val result: Deferred<BaseModel<HomeModel>?> = GlobalScope.async {
            getHomeDataApi()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val result : BaseModel<HomeModel>? = result.await()
            result?.apply {
                processData(this)
            }
        }
    }
    fun processData(homeResponse: BaseModel<HomeModel>?){
        liveData.postValue(homeResponse)
    }
    fun getHomeDataApi() : BaseModel<HomeModel>? {
        val url = URL("https://api-dot-rafiji-staging.appspot.com/customer/v2/home")
        val httpClient = url.openConnection() as HttpURLConnection
        httpClient.connectTimeout = 5000
        httpClient.readTimeout = 5000
        if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
            try {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                return createModel(data)
            } catch (e: Exception) {

            } finally {
                httpClient.disconnect()
            }
        } else {

        }
        return null
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun createModel(data: String): BaseModel<HomeModel>? {
        return BaseModel(
            code = null,
            msg = null,
            data = GsonBuilder().create().fromJson(data, HomeModel::class.java)
        )
    }
}
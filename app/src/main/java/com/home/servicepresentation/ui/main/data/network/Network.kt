package com.home.servicepresentation.ui.main.data.network

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.base.BaseObservable
import com.home.servicepresentation.ui.main.utils.CallApiTask

class Network() {

    private val baseUrl = "https://api-dot-rafiji-staging.appspot.com/customer/v2/"
    var baseObservable =
        BaseObservable()

    fun getHomeData(context: Context){
        var result = CallApiTask(context, "home").execute(baseUrl+"home").get()
        processResult(result)
    }

    fun getDetailData(context: Context){
        var result = CallApiTask(context, "detail").execute(baseUrl+"categories/carwash/services").get()
        processResult(result)
    }

    fun processResult(result: BaseModel<*>?){
        if (result!=null)
            baseObservable.addModel(result)
        else
            baseObservable.addModel(
                BaseModel(
                    msg = "No response was received from the server.",
                    data = null
                )
            )
    }
}
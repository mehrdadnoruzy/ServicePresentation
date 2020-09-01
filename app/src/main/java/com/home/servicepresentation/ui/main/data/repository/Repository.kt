package com.home.servicepresentation.ui.main.data.repository

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.base.BaseObservable
import com.home.servicepresentation.ui.main.data.network.Network
import java.util.*

class Repository(private val network: Network): Observer {

    var baseObservable =
        BaseObservable()

    init {
        network.baseObservable.addObserver(this)
    }

    fun getHomePageData(context: Context) =
        network.getHomeData(context)


    fun getDetailData(context: Context) =
        network.getDetailData(context)


    override fun update(o: Observable?, arg: Any?) =
        baseObservable.addModel(arg as BaseModel<*>)


}
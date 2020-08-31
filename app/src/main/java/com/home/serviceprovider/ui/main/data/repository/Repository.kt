package com.home.serviceprovider.ui.main.data.repository

import android.content.Context
import com.home.serviceprovider.ui.main.data.models.base.BaseModel
import com.home.serviceprovider.ui.main.data.models.base.BaseObservable
import com.home.serviceprovider.ui.main.data.network.Network
import java.util.*

class Repository(private val network: Network): Observer {

    var baseObservable =
        BaseObservable()

    init {
        network.baseObservable.addObserver(this)
    }

    fun getHomePageData(context: Context) =
        network.getHomeData(context)


    fun getCarwashData() =
        network.getCarwashData()


    override fun update(o: Observable?, arg: Any?) =
        baseObservable.addModel(arg as BaseModel<*>)


}
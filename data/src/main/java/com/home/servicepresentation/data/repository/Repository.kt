package com.home.servicepresentation.data.repository

import android.content.Context
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.data.network.Network

class Repository(private val network: Network) {


    suspend fun getHomePageData(context: Context) : BaseModel<HomeModel>? =
        network.getHomeData(context)


    suspend fun getDetailData(context: Context) : BaseModel<DetailModel>? =
        network.getDetailData(context)


}
package com.home.servicepresentation.ui.main.data.repository

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.network.Network
import kotlinx.coroutines.Deferred

class Repository(private val network: Network) {


    suspend fun getHomePageData(context: Context) : BaseModel<HomeModel>? =
        network.getHomeData(context)


    suspend fun getDetailData(context: Context) : BaseModel<DetailModel>? =
        network.getDetailData(context)


}
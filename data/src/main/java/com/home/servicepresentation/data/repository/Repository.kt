package com.home.servicepresentation.data.repository

import com.home.servicepresentation.data.SingletonHolder
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.data.network.Network

class Repository private constructor(private val network: Network) {

    companion object : SingletonHolder<Repository, Network>(::Repository)

    suspend fun getHomePageData(): BaseModel<HomeModel>? =
        network.getHomeData()


    suspend fun getDetailData(): BaseModel<DetailModel>? =
        network.getDetailData()


}
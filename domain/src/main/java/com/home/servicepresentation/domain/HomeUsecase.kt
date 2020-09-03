package com.home.servicepresentation.domain

import android.content.Context
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.data.repository.Repository

class HomeUsecase(private val repository: Repository) : UseCase<HomeModel> {

    override suspend fun executeAsync(context: Context): BaseModel<HomeModel>? =
        repository.getHomePageData(context)
}
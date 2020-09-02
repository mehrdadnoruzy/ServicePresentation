package com.home.servicepresentation.ui.main.domain

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.repository.Repository
import kotlinx.coroutines.Deferred

class HomeUsecase(private val repository: Repository) : UseCase<HomeModel> {

    override suspend fun executeAsync(context: Context): BaseModel<HomeModel>? =
        repository.getHomePageData(context)
}
package com.home.servicepresentation.ui.main.domain

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.repository.Repository
import kotlinx.coroutines.Deferred

class DetailUsecase(private val repository: Repository) : UseCase<DetailModel> {

    override suspend fun executeAsync(context: Context): BaseModel<DetailModel>? =
        repository.getDetailData(context)
}
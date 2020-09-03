package com.home.servicepresentation.domain

import android.content.Context
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.repository.Repository

class DetailUsecase(private val repository: Repository) : UseCase<DetailModel> {

    override suspend fun executeAsync(context: Context): BaseModel<DetailModel>? =
        repository.getDetailData(context)
}
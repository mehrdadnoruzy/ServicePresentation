package com.home.servicepresentation.domain

import android.content.Context
import com.home.servicepresentation.data.models.base.BaseModel

interface UseCase<T> {

    suspend fun executeAsync(context: Context) : BaseModel<T>?
}
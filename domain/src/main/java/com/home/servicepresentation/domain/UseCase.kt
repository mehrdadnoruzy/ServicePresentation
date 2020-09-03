package com.home.servicepresentation.domain

import com.home.servicepresentation.data.models.base.BaseModel

interface UseCase<T> {

    suspend fun executeAsync() : BaseModel<T>?
}
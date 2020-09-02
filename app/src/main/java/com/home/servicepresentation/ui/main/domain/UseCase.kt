package com.home.servicepresentation.ui.main.domain

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import kotlinx.coroutines.Deferred
import java.util.*

interface UseCase<T> {

    fun executeAsync(context: Context) : Deferred<BaseModel<T>?>
}
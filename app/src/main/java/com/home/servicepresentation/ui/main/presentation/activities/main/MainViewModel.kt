package com.home.servicepresentation.ui.main.presentation.activities.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.base.BaseObservable
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailObservable
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.models.home.HomeObservable
import com.home.servicepresentation.ui.main.domain.DetailUsecase
import com.home.servicepresentation.ui.main.domain.HomeUsecase
import java.util.*

class MainViewModel(
    private val homeUsecase: HomeUsecase,
    private val detailUsecase: DetailUsecase
) : ViewModel(), Observer {

    var homeObservable = HomeObservable()
    var detailObservable = DetailObservable()
    var baseObservable = BaseObservable()

    init {
        homeUsecase.baseObservable.addObserver(this)
        detailUsecase.baseObservable.addObserver(this)
    }

    fun getHomeData(context: Context) {
        homeUsecase.execute(context)
    }

    fun getDetailData(context: Context) {
        detailUsecase.execute(context)
    }

    override fun update(o: Observable?, arg: Any?) {
        when ((arg as BaseModel<*>).data) {
            is HomeModel -> homeObservable.addModel(arg.data as HomeModel)
            is DetailModel -> detailObservable.addModel(arg.data as DetailModel)
            else -> baseObservable.addModel(arg)
        }
    }
}
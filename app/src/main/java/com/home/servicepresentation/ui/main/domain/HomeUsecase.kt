package com.home.servicepresentation.ui.main.domain

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.models.home.HomeObservable
import com.home.servicepresentation.ui.main.data.repository.Repository
import java.util.*

class HomeUsecase(private val repository: Repository): UseCase {

    var homeObservable = HomeObservable()

    init {
        repository.baseObservable.addObserver(this)
    }

    override fun execute(context: Context) =
        repository.getHomePageData(context)


    override fun update(o: Observable?, arg: Any?) {
        if ((arg as BaseModel<*>).data!=null) {
            homeObservable.addModel(arg.data as HomeModel)
        }
    }
}
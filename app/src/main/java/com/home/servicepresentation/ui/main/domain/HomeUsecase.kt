package com.home.servicepresentation.ui.main.domain

import android.content.Context
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.base.BaseObservable
import com.home.servicepresentation.ui.main.data.repository.Repository
import java.util.*

class HomeUsecase(private val repository: Repository) : UseCase {

    var baseObservable = BaseObservable()

    init {
        repository.baseObservable.addObserver(this)
    }

    override fun execute(context: Context) =
        repository.getHomePageData(context)


    override fun update(o: Observable?, arg: Any?) {
        baseObservable.addModel(arg as BaseModel<*>)
    }
}
package com.home.serviceprovider.ui.main.data.models.base

import com.home.serviceprovider.ui.main.data.models.base.BaseModel
import java.util.*

class BaseObservable : Observable() {

    fun addModel(baseModel: BaseModel<*>){
        setChanged()
        notifyObservers(baseModel)
    }
}
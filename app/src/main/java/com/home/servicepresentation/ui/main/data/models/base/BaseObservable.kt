package com.home.servicepresentation.ui.main.data.models.base

import java.util.*

class BaseObservable : Observable() {

    fun addModel(baseModel: BaseModel<*>){
        setChanged()
        notifyObservers(baseModel)
    }
}
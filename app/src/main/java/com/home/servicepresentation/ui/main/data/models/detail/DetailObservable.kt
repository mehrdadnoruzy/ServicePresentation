package com.home.servicepresentation.ui.main.data.models.detail

import java.util.*

class DetailObservable : Observable() {

    fun addModel(detailModel: DetailModel){
        setChanged()
        notifyObservers(detailModel)
    }
}
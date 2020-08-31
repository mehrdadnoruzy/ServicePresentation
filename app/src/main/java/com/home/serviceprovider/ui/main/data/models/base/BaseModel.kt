package com.home.serviceprovider.ui.main.data.models.base

data class BaseModel<T>(private val code: Int?=-1, private val msg: String?="", val data: T?)
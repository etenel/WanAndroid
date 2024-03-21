package com.wls.base.entity

import kotlinx.serialization.Serializable


@Serializable
data class BaseData<T>(
    val errorCode: Int = 0,
    val errorMsg: String = "",
    val `data`: T?=null,
)
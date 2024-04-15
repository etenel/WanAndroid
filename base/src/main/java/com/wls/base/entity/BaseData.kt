package com.wls.base.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class BaseData<T>(
    val errorCode: Int = 0,
    val errorMsg: String = "",
    val `data`: T?=null,
)
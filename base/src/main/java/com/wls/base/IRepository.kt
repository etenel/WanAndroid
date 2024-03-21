package com.wls.base

/**
 * hilt注入使用，viewModel中指定类型
 * ViewModel @Inject constructor(private val repository: HomeRepository)
 *
 * 也可以每个repository单独写interface，一个interface对应一个repository
 * ViewModel @Inject constructor(private val repository: IRepository)
 */
interface IRepository {

}
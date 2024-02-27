package com.wls.poke.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.ResultState
import com.wls.poke.entity.asResult
import com.wls.poke.repository.IHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IHomeRepository) : ViewModel() {
     var banner:MutableState<List<BannerEntity>> = mutableStateOf(listOf())
    init {
        banner()
    }
    fun banner(){
        viewModelScope.launch {
            repository.banners().asResult()
                .collect {
                    when (it) {
                        is ResultState.Empty -> {

                        }

                        is ResultState.Error -> {

                        }

                        ResultState.Loading -> {

                        }

                        is ResultState.Success -> {
                         banner.value=it.data
                        }

                        is ResultState.UnLogin -> {

                        }

                        else -> {}
                    }
                }
        }
    }
}



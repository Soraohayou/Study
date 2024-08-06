package com.soraohayou.study.flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * @description:
 * @author: admin
 * @date: 2024/6/21
 * @email: 1145338587@qq.com
 */
class ViewModel : ViewModel() {

    private var livedata = MutableLiveData<MutableList<String>>();

    fun function() {
        viewModelScope.launch {
            Repository().getData().collect{
                livedata.value = it
            }
        }
    }

}



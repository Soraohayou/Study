package com.soraohayou.study.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


/**
 * @description:
 * @author: admin
 * @date: 2024/6/27
 * @email: 1145338587@qq.com
 */
class ViewModel : ViewModel() {

    public fun test(){
        viewModelScope.launch {

        }
    }

}
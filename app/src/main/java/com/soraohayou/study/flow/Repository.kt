package com.soraohayou.study.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @description:
 * @author: admin
 * @date: 2024/6/21
 * @email: 1145338587@qq.com
 */
class Repository {

    suspend fun getData(): Flow<MutableList<String>> {
        return flow{
            emit(mutableListOf("1","2"))
        }.flowOn(Dispatchers.IO)
    }

}
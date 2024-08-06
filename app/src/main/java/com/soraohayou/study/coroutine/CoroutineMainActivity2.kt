package com.soraohayou.study.coroutine

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.soraohayou.study.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.launch

/**
 *
 */
class CoroutineMainActivity2 : AppCompatActivity() {

    private val TAG = "CoroutineMainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_main2)

        val mHandleThread: HandlerThread = HandlerThread("test");

        var mHandler: Handler = mHandleThread.run {
            start()
            Handler(this.looper)
        }
        CoroutineScope(mHandler.asCoroutineDispatcher("123")).launch {

        }
    }
}
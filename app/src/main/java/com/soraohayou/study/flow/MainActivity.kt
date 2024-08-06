package com.soraohayou.study.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.soraohayou.study.R

class MainActivity : AppCompatActivity() {

    private val viewModel = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_main)



    }

    fun a(view: View) = viewModel.function()

}
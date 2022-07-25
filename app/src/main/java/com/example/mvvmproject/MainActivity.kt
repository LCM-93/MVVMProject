package com.example.mvvmproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.launcher.ARouter
import cm.module.core.config.ARouterPath

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            ARouter.getInstance().build(ARouterPath.User.LOGIN).navigation()
        }
    }
}

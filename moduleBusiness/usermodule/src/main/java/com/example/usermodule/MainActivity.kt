package com.example.usermodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import cm.module.core.config.ARouterPath
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toLogin.setOnClickListener {
            ARouter.getInstance().build(ARouterPath.User.LOGIN).navigation()
        }
    }
}

package com.lcm.mvvm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019-06-24 16:53
 * Desc:
 * *****************************************************************
 */
abstract class BaseViewModelFactory : ViewModelProvider.NewInstanceFactory() {


    /**
     * if(modelClass.isAssignableFrom(xxxViewModel::class.java!!)){
     *   return xxxViewModel() as T
     * }
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}
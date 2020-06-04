package com.example.usermodule.utils

import cm.mvvm.core.base.event.LoadingStatus
import cm.mvvm.core.base.event.VMEvent
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/6/4 15:17
 * Desc:
 * *****************************************************************
 */
class MatcherLoadingStatus(private val status: LoadingStatus.Status) : Matcher<VMEvent<LoadingStatus>> {
    var vmValue: LoadingStatus? = null
    override fun describeTo(description: Description?) {
        description?.appendText("LoadingStatus is $status")
    }
    override fun describeMismatch(item: Any?, mismatchDescription: Description?) {
        mismatchDescription?.appendText("LoadingStatus is ${vmValue?.status}")
    }
    override fun _dont_implement_Matcher___instead_extend_BaseMatcher_() {
    }

    override fun matches(item: Any?): Boolean {
        if (item is VMEvent<*>) {
            vmValue = item.getContentIfNotHandled() as LoadingStatus
            return vmValue?.status == status
        }
        return false
    }

}
package cm.mvvm.core.base.lazy

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/6/2 15:25
 * Desc: 支持懒加载的FragmentAdapter
 * *****************************************************************
 */
abstract class FragmentLazyPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

abstract class FragmentLazyStatePageAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
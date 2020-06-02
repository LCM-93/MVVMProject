package cm.mvvm.core.base.lazy

import androidx.fragment.app.Fragment

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020/6/2 15:01
 * Desc: 懒加载Fragment
 * https://github.com/AndyJennifer/AndroidxLazyLoad
 * *****************************************************************
 */
abstract class LazyFragment : Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()
}
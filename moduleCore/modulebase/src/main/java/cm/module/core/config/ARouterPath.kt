package cm.module.core.config

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 15:11
 * Desc: 统一管理 ARouter地址
 * *****************************************************************
 */
object ARouterPath {
    private const val ACTIVITY_BASE = "/Demo/Activity"
    private const val FRAGMENT_BASE = "/Demo/Fragment"

    object User{ //分模块管理
        const val LOGIN = "$ACTIVITY_BASE/LoginActivity"

        const val REGISTER = "$ACTIVITY_BASE/RegisterActivity"
    }



}
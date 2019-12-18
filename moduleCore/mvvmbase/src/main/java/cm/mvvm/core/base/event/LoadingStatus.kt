package cm.mvvm.core.base.event

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 14:47
 * Desc:
 * *****************************************************************
 */
class LoadingStatus(var status: Status? = Status.NORMAL, var msg: String? = "", var tag: Any? = null) {
    enum class Status {
        LOADING, LOAD_SUCCESS, LOAD_FAILED, NORMAL
    }

    override fun toString(): String {
        return "LoadStatus(status=$status, msg=$msg, tag=$tag)"
    }

}
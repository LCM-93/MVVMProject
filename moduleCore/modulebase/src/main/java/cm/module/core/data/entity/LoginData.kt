package cm.module.core.data.entity

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/10/24 16:56
 * Desc:
 * *****************************************************************
 */
class LoginData {
    var email:String? = null
    var icon:String? = null
    var id:String? = null
    var nickname:String? = null
    var password:String? = null
    var publicName:String? = null
    var token:String? = null
    var type:Int ? = 0
    var username:String? = null
    override fun toString(): String {
        return "LoginData(email=$email, icon=$icon, id=$id, nickname=$nickname, password=$password, publicName=$publicName, token=$token, type=$type, username=$username)"
    }


}
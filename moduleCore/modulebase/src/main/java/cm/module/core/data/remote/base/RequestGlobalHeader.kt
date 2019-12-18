package cm.module.core.data.remote.base

import cm.mvvm.core.repository.http.GlobalHttpHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/9/17 10:03
 * Desc:
 * *****************************************************************
 */
class RequestGlobalHeader : GlobalHttpHandler {
    override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response =
        response

    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        return request.newBuilder()
            .header("Content-type", "application/json")
            .header("charset", "UTF-8")
            .header("Accept", "application/json")
            .build()
    }
}
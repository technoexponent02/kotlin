package com.technoexponent.sampleandroidapp.Callback


import java.net.SocketTimeoutException
import java.net.UnknownHostException

import retrofit2.adapter.rxjava.HttpException

/**
 * Created by Technoexponent on 15-Sep-17.
 */
object RetrofitError {

    fun codeToErrorMessage(code: Int): String {
        return if (code >= 400 && code < 500)
            "serverIssues"
        else if (code >= 400 && code < 500)
            "serverIssues"
        else
            "unResolveErrorr"
    }

    fun showErrorMessage(error: Throwable): String {
        if (error is SocketTimeoutException) {
            return "No Internet Connection"
        } else if (error is UnknownHostException) {
            return "serverIssues"
        } else if (error is HttpException) {
            val code = error.code()
            return codeToErrorMessage(code)
        } else {
            return "unResolveErrorr"
        }
    }
}

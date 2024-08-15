package com.spyker3d.easyjob.network.data.dto.responses

open class Response {
    companion object {
        const val SUCCESS = 200
        const val FAILURE = 500
        const val NOT_FOUND = 404
        const val NO_INTERNET = -1
        const val ILLEGAL_STATE = -2
    }

    var resultCode = 0
    var errorMessage: String? = null
}

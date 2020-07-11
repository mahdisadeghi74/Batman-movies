package com.yara.project.batmanproject.model

class Response<T>(
    var status: Status,
    var data: T ?= null,
    var message: String ?= ""
) {


    companion object{
        fun <T> success(data: T? = null): Response<T>{
            return Response(status = Status.SUCCESS, data = data)
        }

        fun <T> loading(data: T? = null): Response<T>{
            return Response(status = Status.LOADING, data = data)
        }

        fun <T> error(data: T? = null, message: String? = null): Response<T>{
            return Response(status = Status.ERROR, data = data, message = message)
        }
    }
    enum class Status {
        SUCCESS,
        LOADING,
        ERROR
    }
}
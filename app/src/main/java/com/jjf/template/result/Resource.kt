package com.jjf.template.result

import com.jjf.template.result.Status.*

/**
 * @author xj
 * date: 18-11-8
 * description :
 */
data class Resource<T>(val status: Status, val data: T?, private val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}

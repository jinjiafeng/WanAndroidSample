package com.jjf.template.util.lifecycle

import androidx.lifecycle.LiveData

/**
 * @author xj
 * date: 18-11-8
 * description : A LiveData class that has `null` value.
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}

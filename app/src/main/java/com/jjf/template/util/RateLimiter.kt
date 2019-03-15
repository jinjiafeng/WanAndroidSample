package com.jjf.template.util

import android.os.SystemClock
import androidx.collection.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * @author xj
 * date: 18-11-9
 * description : Utility class that decides whether we should fetch some data or not.
 */
class RateLimiter<KEY>(timeout: Int, timeUnit: TimeUnit) {

    private val mTimestamps = ArrayMap<KEY, Long>()
    private val mTimeout: Long = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = mTimestamps[key]
        val now = now()
        if (lastFetched == null) {
            mTimestamps[key] = now
            return true
        }
        if (now - lastFetched > mTimeout) {
            mTimestamps[key] = now
            return true
        }
        return false
    }

    private fun now(): Long {
        return SystemClock.uptimeMillis()
    }

    /**
     * 获取失败 onFetchFailed()中调用
     * @param key mTimestamps key
     */
    fun reset(key: KEY) {
        mTimestamps.remove(key)
    }
}

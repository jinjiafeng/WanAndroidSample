package com.jjf.template.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * @author xj
 * date: 19-3-14
 * description : 解决viewPager和SwipeRefreshLayout的滑动冲突
 */

class CustomSwipeRefreshLayout : SwipeRefreshLayout {
    private var startGestureX: Float = 0f
    private var startGestureY: Float = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startGestureX = event.x
                startGestureY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.x - startGestureX) > Math.abs(event.y - startGestureY)) {
                    return false
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }
}

package com.jjf.template.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by facexxyz on 18-2-6.
 */

object SoftInputUtil {
    /**
     * 显示软键盘
     *
     * @param context
     */
    fun showSoftInput(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    fun showSoftInput(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    fun hideSoftInput(context: Context, view: View) {
        val immHide = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        immHide.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

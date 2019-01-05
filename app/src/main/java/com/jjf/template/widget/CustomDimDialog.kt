package com.jjf.template.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.res.ResourcesCompat
import com.jjf.template.R

/**
 * @author xj
 * date: 19-1-5
 * description :
 */
class CustomDimDialog(context: Context?) : AppCompatDialog(context, R.style.Theme_cloud_Dialog) {

    init {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.run {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setContentView(view: View?) {
        if (view != null) {
            super.setContentView(wrap(view))
        }
    }

    private fun wrap(content: View): View {
        val res = context.resources
        val verticalMargin = res.getDimensionPixelSize(R.dimen.dialog_vertical_margin)
        val horizontalMargin = res.getDimensionPixelSize(R.dimen.dialog_horizontal_margin)

        return FrameLayout(context).apply {
            addView(content, FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
                setMargins(verticalMargin, horizontalMargin, horizontalMargin, verticalMargin)
            })
            val rect = Rect()
            setOnTouchListener { v, event ->
                when (event.action) {
                    // The FrameLayout is technically inside the dialog, but we treat it as outside.
                    MotionEvent.ACTION_DOWN -> {
                        content.getGlobalVisibleRect(rect)
                        if (!rect.contains(event.x.toInt(), event.y.toInt())) {
                            cancel()
                            true
                        } else {
                            false
                        }
                    }

                    else -> false
                }
            }

            background = ColorDrawable(ResourcesCompat.getColor(res, R.color.scrim, context.theme))
        }
    }
}

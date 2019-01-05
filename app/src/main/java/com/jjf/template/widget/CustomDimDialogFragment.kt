package com.jjf.template.widget

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

/**
 * @author xj
 * date: 19-1-5
 * description :
 */
class CustomDimDialogFragment:AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return CustomDimDialog(context)
    }
}
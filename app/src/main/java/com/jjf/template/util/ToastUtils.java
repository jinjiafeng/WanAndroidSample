package com.jjf.template.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.jjf.template.App;

import androidx.annotation.StringRes;


public class ToastUtils {

    private static Toast sToast;

    public static void showShort(String s) {
        show(s, Toast.LENGTH_SHORT);
    }
    public static void showShort(@StringRes int  resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    private static void show(@StringRes int  resId, int duration) {
        final String msg = App.instance.getString(resId);
        show(msg, duration);
    }

    private static void show(String msg, int duration) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            showToast(msg, duration);
        } else {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(() -> showToast(msg, duration));
        }
    }

    private static void showToast(String msg, int duration) {
        if (sToast != null) sToast.cancel();
        sToast = Toast.makeText(App.instance, msg, duration);
        sToast.show();
    }

    public static void showLong(String s) {
        //        Toast.makeText(MainApplication.getInstance(), s, Toast.LENGTH_LONG).show();
        show(s, Toast.LENGTH_LONG);
    }
}

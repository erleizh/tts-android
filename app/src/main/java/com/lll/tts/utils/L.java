package com.lll.tts.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.lll.tts.BuildConfig;
import com.lll.tts.R;

import java.util.Locale;

/**
 * 作者:  lll
 * 日期： 2017/3/23
 */
public class L {

    private static boolean LOG_ENABLE = BuildConfig.DEBUG;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static String TAG;

    public static void init(Context context) {
        L.context = context;
        TAG = context.getString(R.string.app_name);
    }

    public static void i(String format, Object... msg) {
        if(LOG_ENABLE) Log.i(TAG, String.format(Locale.CHINA, format, msg));
    }
}



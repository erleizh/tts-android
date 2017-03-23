package com.lll.tts;

import android.app.Application;

import com.lll.tts.utils.L;

/**
 * 作者:  lll
 * 日期： 2017/3/23
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        L.init(this.getApplicationContext());
    }
}

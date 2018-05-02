package com.popo.leancloudtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by popo on 2018/5/1.
 */

public class SharedPreferenceManeger {
    private static SharedPreferences sharedPreferences=null;
    private SharedPreferenceManeger(){
    }
    public static SharedPreferences getInstance(Context context){
        if(sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("userdata",0);
        }
        return sharedPreferences;
    }
}

package com.firstpoli.spotx.utils

import android.util.Log
import com.firstpoli.spotx.BuildConfig
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {

        fun log(msg: String?) {
            if (BuildConfig.DEBUG) {
                if (!msg.isNullOrEmpty()) {
                    Log.d("LogHelper", msg)
                }
            }
        }

        fun logE(msg: String?) {
            if (BuildConfig.DEBUG) {
                if (!msg.isNullOrEmpty()) {
                    Log.e("LogHelper", msg)
                }
            }
        }


        fun formatRecordDatetime(datetime: Long): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
            return simpleDateFormat.format(datetime)
        }

    }
}
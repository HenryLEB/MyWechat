package com.sunnyweather.android.logic.dao

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.mywechat.WechatApplication
import com.google.gson.Gson
import com.sunnyweather.android.logic.model.Place

object PlaceDao {

    fun savePlace(place: Place) {
        Log.d("这里是SAVE","222222222222222")
//        sharedPreferences().edit {
////            putString("place", Gson().toJson(place))
////            putString("place", "北京")
//            Log.d("这里是SAVE","3333333333333")
//        }
        WechatApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
        Log.d("这里是SAVE","1111111111111111")
//        val editor = sharedPreferences().edit()
//        editor.putString("place", Gson().toJson(place))
//        editor.apply()
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        WechatApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}
package com.green.stockinfo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyApplication : Application() {
    private val TAG = javaClass.simpleName

    override fun onCreate() {
        SharedPreferenceManager.init(applicationContext)

        super.onCreate()
    }
}

object SharedPreferenceManager {
    private const val PREF_TOKEN = "token"
    private const val PREF_NAME = "name"
    private const val PREF_MODE = Context.MODE_PRIVATE

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor)->Unit) {
        val editor = edit()
        operation(edit())
        edit().apply()
    }

    var token: String
        get() = sharedPreferences.getString(PREF_TOKEN, "").toString()
        set(value) = sharedPreferences.edit {
            it.putString(PREF_TOKEN, value)
        }
}

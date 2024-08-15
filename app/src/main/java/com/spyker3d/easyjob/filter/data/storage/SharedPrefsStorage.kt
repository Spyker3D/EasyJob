package com.spyker3d.easyjob.filter.data.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.spyker3d.easyjob.filter.domain.model.FilterGeneral

const val SPECIFIC_FILTER_KEY = "saved_specific_filter"
const val FINAL_FILTER_KEY = "final_saved_filter"

class SharedPrefsStorage(private val sharedPreferences: SharedPreferences, private val gson: Gson) :
    FilterStorage {
    override fun saveFinalFilterParameters(filter: FilterGeneral) {
        val json = gson.toJson(filter)
        sharedPreferences.edit().putString(FINAL_FILTER_KEY, json).apply()
    }

    override fun saveSpecificFilterParameters(filter: FilterGeneral) {
        val json = gson.toJson(filter)
        sharedPreferences.edit().putString(SPECIFIC_FILTER_KEY, json).apply()
    }

    override fun getAllFinalFilterParameters(): FilterGeneral {
        val json = sharedPreferences.getString(FINAL_FILTER_KEY, null)
            ?: return FilterGeneral()
        return gson.fromJson(json, FilterGeneral::class.java)
    }

    override fun getAllSavedParameter(): FilterGeneral {
        val json = sharedPreferences.getString(SPECIFIC_FILTER_KEY, null)
            ?: return FilterGeneral()
        return gson.fromJson(json, FilterGeneral::class.java)
    }

    override fun clearAllFilterParameters() {
        val editor = sharedPreferences.edit()
        editor.remove(FINAL_FILTER_KEY).apply()
    }

    override fun clearAllSavedParameters() {
        val editor = sharedPreferences.edit()
        editor.remove(SPECIFIC_FILTER_KEY).apply()
    }
}

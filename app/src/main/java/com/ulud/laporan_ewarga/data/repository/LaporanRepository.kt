package com.ulud.laporan_ewarga.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ulud.laporan_ewarga.domain.model.Laporan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "laporan_app")

class LaporanRepository(private val context: Context) {

    private val LAPORAN_LIST_KEY = stringPreferencesKey("laporan_list")
    private val gson = Gson()

    val allLaporan: Flow<List<Laporan>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[LAPORAN_LIST_KEY] ?: "[]"
            val type = object : TypeToken<List<Laporan>>() {}.type
            gson.fromJson(jsonString, type) ?: emptyList()
        }

    suspend fun addLaporan(laporan: Laporan) {
        context.dataStore.edit { preferences ->
            val currentListJson = preferences[LAPORAN_LIST_KEY] ?: "[]"
            val listType = object : TypeToken<MutableList<Laporan>>() {}.type
            val currentList: MutableList<Laporan> = gson.fromJson(currentListJson, listType) ?: mutableListOf()

            currentList.add(0, laporan)

            preferences[LAPORAN_LIST_KEY] = gson.toJson(currentList)
        }
    }
}
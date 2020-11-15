package com.example.ubb_pdm_android

import android.util.Log

object ItemRepository {
    suspend fun getAll(): List<Item> {
        Log.i(TAG, "getAll");
        return ItemApi.service.getAll()
    }
}
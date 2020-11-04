package com.example.ubb_pdm_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubb_pdm_android.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemListViewModel : ViewModel() {
    private val mutableItems = MutableLiveData<List<Item>>().apply { value = emptyList() }

    val items: LiveData<List<Item>> = mutableItems

    init {
        viewModelScope.launch {
            while (true) {
                val index = simulateNewItemNotification()
                createItem(index)
            }
        }
    }

    fun createItem(position: Int): Unit {
        val list = mutableListOf<Item>()
        list.addAll(mutableItems.value!!)
        list.add(Item(position.toString(), "Item " + position))
        mutableItems.value = list
    }

    var index = 100;
    suspend fun simulateNewItemNotification(): Int = withContext(Dispatchers.Default) {
        delay(1000);
        return@withContext ++index
    }
}

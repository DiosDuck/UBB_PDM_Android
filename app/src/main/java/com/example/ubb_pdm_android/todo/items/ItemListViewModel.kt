package com.example.ubb_pdm_android.todo.items

import android.app.Application
import android.util.AndroidException
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.ubb_pdm_android.core.Result
import com.example.ubb_pdm_android.core.TAG
import com.example.ubb_pdm_android.todo.data.Item
import com.example.ubb_pdm_android.todo.data.ItemRepository
import com.example.ubb_pdm_android.todo.data.local.TodoDatabase

class ItemListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableItems = MutableLiveData<List<Item>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val items: LiveData<List<Item>>
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    val itemRepository: ItemRepository

    init {
        val itemDao = TodoDatabase.getDatabase(application, viewModelScope).itemDao()
        itemRepository = ItemRepository(itemDao)
        items = itemRepository.items
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "refresh...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = itemRepository.refresh()) {
                is Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }
}

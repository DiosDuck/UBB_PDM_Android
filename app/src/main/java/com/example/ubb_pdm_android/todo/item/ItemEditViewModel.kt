package com.example.ubb_pdm_android.todo.item

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.ubb_pdm_android.core.Result
import com.example.ubb_pdm_android.core.TAG
import com.example.ubb_pdm_android.todo.data.Item
import com.example.ubb_pdm_android.todo.data.ItemRepository
import com.example.ubb_pdm_android.todo.data.local.TodoDatabase

class ItemEditViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    val itemRepository: ItemRepository

    init{
        val itemDao=TodoDatabase.getDatabase(application,viewModelScope).itemDao()
        itemRepository= ItemRepository(itemDao)
    }

    fun getItemById(itemId: String): LiveData<Item> {
        Log.v(TAG, "getItemById...")
        return itemRepository.getById(itemId)
    }

    fun saveOrUpdateItem(item:Item) {
        viewModelScope.launch {
            Log.v(TAG, "saveOrUpdateItem...");
            mutableFetching.value = true
            mutableException.value = null
            val result: Result<Item>
            if (item._id.isNotEmpty()) {
                result = itemRepository.update(item)
            } else {
                result = itemRepository.save(item)
            }
            when(result) {
                is Result.Success -> {
                    Log.d(TAG, "saveOrUpdateItem succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "saveOrUpdateItem failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableCompleted.value = true
            mutableFetching.value = false
        }
    }

    fun delete(item: Item){
        viewModelScope.launch {
            Log.v(TAG, "delete...");
            mutableFetching.value = true
            mutableException.value = null
            val result: Result<Item>
            if (item._id.isNotEmpty()) {
                result = itemRepository.delete(item)
            } else {
                result = itemRepository.delete(item)
            }
            when(result) {
                is Result.Success -> {
                    Log.d(TAG, "delete succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "delete failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableCompleted.value = true
            mutableFetching.value = false
        }
    }
}

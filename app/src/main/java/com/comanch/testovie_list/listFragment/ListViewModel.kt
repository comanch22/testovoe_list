package com.comanch.testovie_list.listFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comanch.testovie_list.LiveDataEvent
import com.comanch.testovie_list.dataBase.StarTrackDao
import com.comanch.testovie_list.dataBase.StarTrackData
import com.comanch.testovie_list.network.ISSLocationApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val database: StarTrackDao) : ViewModel() {

    val items = database.getAllItems()

    private val _navigateToDetailFragment = MutableLiveData<LiveDataEvent<Long?>>()
    val navigateToDetailFragment: LiveData<LiveDataEvent<Long?>>
        get() = _navigateToDetailFragment

    private val _toast = MutableLiveData<LiveDataEvent<String?>>()
    val toast: LiveData<LiveDataEvent<String?>>
        get() = _toast

    private val _itemInsert = MutableLiveData<LiveDataEvent<Long?>>()
    val itemInsert: LiveData<LiveDataEvent<Long?>>
        get() = _itemInsert

    fun getISSLocation(id: Long) {

        viewModelScope.launch {
            try {
                val item = database.get(id) ?: return@launch

                val image = ISSLocationApi.retrofitService.getImage()

                image[0].title.also {
                    item.imageTitle = it
                }
                image[0].url.also {
                    item.imageUrl = it
                }

                image[0].explanation.also {

                    val strList = it.split(".")
                    var result = if (strList.size >= 3) {
                        "${strList[0]} ${strList[1]} ${strList[2]}."
                    } else {
                        "${strList[0]}."
                    }
                    result = result.substringBefore("Follow")
                    result = result.substringBefore("follow")
                    item.explanation = result
                }

                item.isSealed = true

                database.update(item)

            } catch (e: Exception) {
                _toast.value = LiveDataEvent("network connection problem: ${e.message}")
            }
        }
    }

    fun insertItem() {

        viewModelScope.launch {

            val size = database.getCount()
            if (size != null && size >= 50) {
                _toast.value = LiveDataEvent("limit reached (50)")
            } else {
                val notSealedItem = database.getNotSealed()
                if (notSealedItem != null){
                    _itemInsert.value = LiveDataEvent(notSealedItem.starTrackId)
                }else {
                    val item = StarTrackData()
                    item.imageTitle = "loading..."
                    _itemInsert.value = LiveDataEvent(database.insertItemGetId(item))
                }
            }
        }
    }

    fun onItemClicked(itemId: Long) {
        _navigateToDetailFragment.value = LiveDataEvent(itemId)
    }

    fun deleteItem() {
        viewModelScope.launch {
            val item = database.getItem() ?: return@launch
            database.delete(item)
        }
    }
}

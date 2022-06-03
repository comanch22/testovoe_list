package com.comanch.testovie_list.detailFragment

import androidx.lifecycle.*
import com.comanch.testovie_list.LiveDataEvent
import com.comanch.testovie_list.dataBase.StarTrackDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val database: StarTrackDao
) : ViewModel() {

    private var _toast = MutableLiveData<LiveDataEvent<String?>>()
    val toast: LiveData<LiveDataEvent<String?>>
        get() = _toast

    private var _imageUrl = MutableLiveData<LiveDataEvent<String?>>()
    val imageUrl: LiveData<LiveDataEvent<String?>>
        get() = _imageUrl

    private var _imageTitle = MutableLiveData<LiveDataEvent<String?>>()
    val imageTitle: LiveData<LiveDataEvent<String?>>
        get() = _imageTitle

    private var _explanation = MutableLiveData<LiveDataEvent<String?>>()
    val explanation: LiveData<LiveDataEvent<String?>>
        get() = _explanation

    fun setDetail(id: Long) {
        viewModelScope.launch {
            val item = database.get(id) ?: return@launch
            _imageTitle.value = LiveDataEvent(item.imageTitle)
            _imageUrl.value = LiveDataEvent(item.imageUrl)
            _explanation.value = LiveDataEvent(item.explanation)
        }
    }
}



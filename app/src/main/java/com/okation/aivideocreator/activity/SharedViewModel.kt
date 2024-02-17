package com.okation.aivideocreator.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.okation.aivideocreator.model.FakeYouEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private var _nameResponse = MutableLiveData<String>()
    var nameResponse = _nameResponse

    private var _imageResponse = MutableLiveData<Int>()
    var imageResponse = _imageResponse

    private var _userInputTextResponse = MutableLiveData<String>()
    var userInputTextResponse = _userInputTextResponse

    private var _fakeYouEntityResponse = MutableLiveData<FakeYouEntity>()
    var fakeYouEntityResponse = _fakeYouEntityResponse

    fun nameSharedViewModel(name: String) {
        _nameResponse.value = name
    }

    fun imageSharedViewModel(image: Int) {
        _imageResponse.value = image
    }

    fun userInputTextSharedViewModel(userInputText: String) {
        _userInputTextResponse.value = userInputText
    }

    fun fakeYouEntitySharedViewModel(fakeYouEntity: FakeYouEntity) {
        _fakeYouEntityResponse.value = fakeYouEntity
    }
}
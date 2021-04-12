package com.example.layouts

import androidx.lifecycle.MutableLiveData

class DataChange {
    val data = MutableLiveData<String>()
    var nbClick = 0
    fun newData() {
        nbClick++
        data.value = "$nbClick"
    }
}

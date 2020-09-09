package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel(){

    private val mutableTempTextViewLiveData: MutableLiveData<String> = MutableLiveData()
    val tempTextViewLiveData: LiveData<String> = mutableTempTextViewLiveData

    fun changeToHello() {
        mutableTempTextViewLiveData.value = "Hello"
    }

    private val mutableTimeCountTextViewLiveData: MutableLiveData<String> = MutableLiveData()
    val tempTimeCountTextViewLiveData: LiveData<String> = mutableTimeCountTextViewLiveData

}
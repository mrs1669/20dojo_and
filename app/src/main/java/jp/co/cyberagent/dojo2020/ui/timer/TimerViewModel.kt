package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel(){
    
    private val mutableTempTextView: MutableLiveData<String> = MutableLiveData()
    val tempTextView: LiveData<String> = mutableTempTextView

    fun changeToHello() {
        mutableTempTextView.value = "Hello"
    }

}
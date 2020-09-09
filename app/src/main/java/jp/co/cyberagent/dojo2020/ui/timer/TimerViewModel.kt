package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel(){
    private var mutableTempTextView: MutableLiveData<String> = MutableLiveData()
    var tempTextView: LiveData<String> = mutableTempTextView

    fun setTempTextView(tempTextView: String) {
        mutableTempTextView.postValue(tempTextView)
    }

    fun getTempTextView(): String {
        return if (mutableTempTextView.value != null) mutableTempTextView.value!! else "test"
    }
}
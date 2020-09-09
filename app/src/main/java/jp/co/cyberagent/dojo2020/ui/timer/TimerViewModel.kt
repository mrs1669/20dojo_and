package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel (context: Context): ViewModel(){
    private var mName: MutableLiveData<String> = MutableLiveData()
    var name: LiveData<String> = mName

    fun setName(name: String) {
        mName.postValue(name)
    }

    fun getName(): String {
        return if (mName.value != null) mName.value!! else "test"
    }
}
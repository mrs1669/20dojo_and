package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimerViewModel : ViewModel(){

    private val mutableTempTextViewLiveData: MutableLiveData<String> = MutableLiveData()
    val tempTextViewLiveData: LiveData<String> = mutableTempTextViewLiveData

    private val mutableTimeCountTextViewLiveData: MutableLiveData<String> = MutableLiveData()
    val timeCountTextViewLiveData: LiveData<String> = mutableTimeCountTextViewLiveData

    var tempCurrentTimeMills: Int = 0

    fun getCurrentDate(): String? {
        val df: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date(System.currentTimeMillis())
        return df.format(date)
    }

    private fun getCurrentTimeMills(): Int{
        return System.currentTimeMillis().toInt()
    }

    private fun getStringCurrentTimeMills(): String{
        return getCurrentTimeMills().toString()
    }

    fun setCurrentTimeMills(){
        tempCurrentTimeMills = getCurrentTimeMills()
    }

    private fun getElapsedTimeMills(): Int{
        return getCurrentTimeMills() - tempCurrentTimeMills
    }

    private fun getStringElapsedTimeMill(): String{
        return getElapsedTimeMills().toString()
    }

    fun changeToHello() {
        mutableTempTextViewLiveData.value = "Hello"
    }

    fun applyMutableTimeCountTextViewLiveData() {
        mutableTimeCountTextViewLiveData.value = getStringElapsedTimeMill()
    }

}
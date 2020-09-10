package jp.co.cyberagent.dojo2020.ui.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimerViewModel : ViewModel(){

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

    private fun getStringElapsedTimeMills(): String{
        return getElapsedTimeMills().toString()
    }

    private fun timeToTimeString(time: Int = 0): String? {
        return if (time < 0) {
            null                                    // 時刻が0未満の場合 null
        } else if (time == 0) {
            "00:00:00:000"                            // ０なら
        } else {
            val h = time / 3600000
            val m = time % 3600000 / 60000
            val s = time % 60000 / 1000
            val ms = time % 1000
            "%1$02d:%2$02d:%3$02d:%4$03d".format(h, m, s, ms)  // 表示に整形
        }
    }

    private fun getFormattedElapsedTime(): String{
        return timeToTimeString(getElapsedTimeMills()) ?: "ElapsedTime cannot refer." // try to use Elvis operator.
    }

    fun applyMutableTimeCountTextViewLiveData() {
        mutableTimeCountTextViewLiveData.value = getFormattedElapsedTime()
    }

}
package jp.co.cyberagent.dojo2020.ui.timer

import android.app.Application
import androidx.lifecycle.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val mutableTimeCountTextViewLiveData: MutableLiveData<String> = MutableLiveData()
    val timeCountTextViewLiveData: LiveData<String> =  mutableTimeCountTextViewLiveData

    var startTimeMills: Int = 0
    var pauseTimeStartMills: Int = 0

    fun getCurrentDate(): String? {
        val df: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date(System.currentTimeMillis())
        return df.format(date)
    }

    fun getCurrentTimeMills(): Int{
        return System.currentTimeMillis().toInt()
    }

    private fun getStringCurrentTimeMills(): String{
        return getCurrentTimeMills().toString()
    }

    fun setStartTimeMills(){
        startTimeMills = getCurrentTimeMills()
    }

    fun setPauseTimeStartMills(){
        pauseTimeStartMills = getCurrentTimeMills()
    }

    private fun getElapsedTimeMills(): Int{
        return getCurrentTimeMills() - startTimeMills
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

    private fun getFormattedCurrentTime(): String{
        return ""
    }

    private fun getPauseTimeMills(): Int{
        return getCurrentTimeMills() - pauseTimeStartMills
    }

    private fun getTimeMills(): Int{
        return getElapsedTimeMills() - getPauseTimeMills()
    }

    private fun getFormattedTime(): String{
        return timeToTimeString(getTimeMills()) ?: "Time cannot refer."
    }

    fun applyMutableTimeCountTextViewLiveData() {
        mutableTimeCountTextViewLiveData.value = getFormattedElapsedTime()
    }

}
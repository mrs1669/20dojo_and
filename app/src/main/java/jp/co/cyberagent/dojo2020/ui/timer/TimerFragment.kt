package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import jp.co.cyberagent.dojo2020.R
import kotlinx.android.synthetic.main.timer_tab.*


class TimerFragment: Fragment(){

    private val timerViewModel: TimerViewModel by viewModels()

    private val handler = Handler()

    private var tappedStartButtonFlag: Int = 0

    private var stopTimerViewFlag: Int = 0

    private var isTimerRunning: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataStore: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)

        isTimerRunning = dataStore?.getBoolean("timerState", false) ?: false

        val runnable = object : Runnable {
            override fun run() {
                if (stopTimerViewFlag == 0){
                    timerViewModel.applyMutableTimeCountTextViewLiveData()
                    handler.postDelayed(this, 36)  // 36ｍｓ後に自分にpost
                }
            }
        }


        if(isTimerRunning){
            handler.post(runnable)
        }else{
            startStopButton.setOnClickListener{
                if (tappedStartButtonFlag == 0){
                    handler.post(runnable)
                    tappedStartButtonFlag = 1
                }
                timerViewModel.setStartTimeMills()
                if (dataStore != null) {
                    //SharedPreferenceに登録したデータを保存
                    with(dataStore.edit()) {
                        putBoolean("timerState", true)
                        apply()
                    }
                }
            }
        }

        restartButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            if (tappedStartButtonFlag == 1){
                startStopButton.setBackgroundResource(R.drawable.restart_icon)
            }
            tappedStartButtonFlag = 0
            if (dataStore != null) {
                //SharedPreferenceに登録したデータを保存
                with(dataStore.edit()) {
                    putBoolean("timerState", false)
                    apply()
                }
            }
        }

        timerViewModel.timeCountTextViewLiveData.observe(viewLifecycleOwner){
            timeCountTextView.text = it
        }
    }

    override fun onStop() {
        super.onStop()
        stopTimerViewFlag = 1
    }

}
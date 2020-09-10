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
    private var isStartFirst: Boolean = true
    private var startTimeMills: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataStore: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)

        isTimerRunning = dataStore?.getBoolean("isTimerRunning", false) ?: false
        isStartFirst = dataStore?.getBoolean("isStartFirst", true) ?: true
        startTimeMills = dataStore?.getInt("startTimeMills", 0) ?: 0

        if(startTimeMills != 0){
            timerViewModel.startTimeMills = this.startTimeMills
        }

        println(isTimerRunning)

        val runnable = object : Runnable {
            override fun run() {
                if (stopTimerViewFlag == 0){
                    timerViewModel.applyMutableTimeCountTextViewLiveData()
                    handler.postDelayed(this, 36)  // 36ｍｓ後に自分にpost
                }
            }
        }


        if(isTimerRunning){
            startStopButton.setImageResource(android.R.drawable.ic_media_pause) // Set button pause image.
            startStopButton.setOnClickListener{
                if(isTimerRunning){
                    startStopButton.setImageResource(android.R.drawable.ic_media_play)
                    isTimerRunning = false
                    restartButton.isClickable = true
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean("isTimerRunning", false) // Set SharedPreferences "isTimerRunning"
                            apply()
                        }
                    }
                    timerViewModel.setPauseTimeMills()
                    handler.removeCallbacks(runnable)
                }else{
                    startStopButton.setImageResource(android.R.drawable.ic_media_pause) // Set button pause image.
                    isTimerRunning = true
                    restartButton.isClickable = false
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean("isTimerRunning", true) // Set SharedPreferences "isTimerRunning"
                            apply()
                        }
                    }
                    handler.post(runnable)
                }
            }
            handler.post(runnable)
        }else{
            startStopButton.setOnClickListener{
                if(isTimerRunning){
                    startStopButton.setImageResource(android.R.drawable.ic_media_play)
                    isTimerRunning = false
                    restartButton.isClickable = true
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean("isTimerRunning", false) // Set SharedPreferences "isTimerRunning"
                            apply()
                        }
                    }
                    timerViewModel.setPauseTimeMills()
                    handler.removeCallbacks(runnable)
                }else{
                    startStopButton.setImageResource(android.R.drawable.ic_media_pause) // Set button pause image.
                    isTimerRunning = true
                    restartButton.isClickable = false
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean("isTimerRunning", true) // Set SharedPreferences "isTimerRunning"
                            apply()
                        }
                    }
                    if(isStartFirst){
                        timerViewModel.setStartTimeMills() // Set start time only first time.
                        isStartFirst = false
                        if (dataStore != null) {
                            with(dataStore.edit()) {
                                putBoolean("isStartFirst", false) // Set SharedPreferences "isStarFirst"
                                putInt("startTimeMills", timerViewModel.getCurrentTimeMills())
                                apply()
                            }
                        }
                    }else{
                        // Do nothing.
                    }
                    handler.post(runnable)
                }
            }
        }

        restartButton.setOnClickListener {
            isStartFirst = true
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
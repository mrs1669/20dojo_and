package jp.co.cyberagent.dojo2020.ui.timer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
    private val initHandler = Handler()

    private var isTimerRunning: Boolean = false
    private var isStartFirst: Boolean = true
    private var startTimeMills: Int = 0
    private var pauseTimeStartMills: Int = 0
    private var sumPauseTimeMills: Int = 0

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
        pauseTimeStartMills = dataStore?.getInt("pauseTimeStartMills",0) ?: 0
        sumPauseTimeMills = dataStore?.getInt("sumPauseTimeMills", 0) ?: 0

        println(timerViewModel.getSumPauseTimeMills())

        if(startTimeMills != 0){
            timerViewModel.startTimeMills = this.startTimeMills
            timerViewModel.pauseTimeStartMills = this.pauseTimeStartMills
            timerViewModel.setSumPauseTimeMills(this.sumPauseTimeMills)
        }

        println(timerViewModel.getSumPauseTimeMills())

        val runnable = object : Runnable {
            override fun run() {
                timerViewModel.applyMutableTimeCountTextViewLiveData()
                // timeCountTextView.text = timerViewModel.getCurrentDate()
                handler.postDelayed(this, 1)  // 36ｍｓ後に自分にpost
            }
        }

        restartButton.setOnClickListener {
            Log.d("restart4", restartButton.isClickable.toString())
            isStartFirst = true
            timerViewModel.init()
            if (dataStore != null) {
                with(dataStore.edit()) {
                    putBoolean("isStartFirst", false) // Set SharedPreferences "isStarFirst"
                    putInt("sumPauseTimeMills", 0) // Set SharePreferences "sumPauseTimeMills"
                    apply()
                }
            }
            timeCountTextView.text = "00:00:00:000"
        }


        if(isTimerRunning){
            startStopButton.setImageResource(android.R.drawable.ic_media_pause) // Set button pause image.
            Log.d("restart", restartButton.isClickable.toString())
            restartButton.isClickable = false
            Log.d("restart2", restartButton.isClickable.toString())
            startStopButton.setOnClickListener {
                if (isTimerRunning) { // When timer running
                    startStopButton.setImageResource(android.R.drawable.ic_media_play)
                    isTimerRunning = false
                    restartButton.isClickable = true
                    timerViewModel.setPauseTimeStartMills()
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean(
                                "isTimerRunning",
                                false
                            ) // Set SharedPreferences "isTimerRunning"
                            putInt("pauseTimeStartMills", timerViewModel.getCurrentTimeMills())
                            apply()
                        }
                    }
                    handler.removeCallbacks(runnable)
                } else { // When timer not running
                    startStopButton.setImageResource(android.R.drawable.ic_media_pause) // Set button pause image.
                    isTimerRunning = true
                    restartButton.isClickable = false
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean(
                                "isTimerRunning",
                                true
                            ) // Set SharedPreferences "isTimerRunning"
                            apply()
                        }
                    }
                    if (isStartFirst) {
                        timerViewModel.setStartTimeMills() // Set start time only first time.
                        isStartFirst = false
                        if (dataStore != null) {
                            with(dataStore.edit()) {
                                putBoolean(
                                    "isStartFirst",
                                    false
                                ) // Set SharedPreferences "isStarFirst"
                                putInt("startTimeMills", timerViewModel.getCurrentTimeMills())
                                apply()
                            }
                        }
                    } else {
                        timerViewModel.addPauseTimeMills()
                        if (dataStore != null) {
                            with(dataStore.edit()) {
                                putInt("sumPauseTimeMills", timerViewModel.getSumPauseTimeMills())
                                apply()
                            }
                        }
                    }
                    handler.post(runnable)
                }
            }
            handler.post(runnable)
        } else { // Not running start.
            timeCountTextView.text = timerViewModel.timeToTimeString(timerViewModel.getTimeMills() - timerViewModel.getPauseTimeMills())
            startStopButton.setOnClickListener {
                if (isTimerRunning) { // When timer running
                    startStopButton.setImageResource(android.R.drawable.ic_media_play)
                    isTimerRunning = false
                    restartButton.isClickable = true
                    timerViewModel.setPauseTimeStartMills()
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean(
                                "isTimerRunning",
                                false
                            ) // Set SharedPreferences "isTimerRunning"
                            putInt("pauseTimeStartMills", timerViewModel.getCurrentTimeMills())
                            apply()
                        }
                    }
                    handler.removeCallbacks(runnable)
                } else { // When timer not running
                    startStopButton.setImageResource(android.R.drawable.ic_media_pause) // Set button pause image.
                    isTimerRunning = true
                    restartButton.isClickable = false
                    if (dataStore != null) {
                        with(dataStore.edit()) {
                            putBoolean(
                                "isTimerRunning",
                                true
                            ) // Set SharedPreferences "isTimerRunning"
                            apply()
                        }
                    }
                    if (isStartFirst) {
                        timerViewModel.setStartTimeMills() // Set start time only first time.
                        isStartFirst = false
                        if (dataStore != null) {
                            with(dataStore.edit()) {
                                putBoolean(
                                    "isStartFirst",
                                    false
                                ) // Set SharedPreferences "isStarFirst"
                                putInt("startTimeMills", timerViewModel.getCurrentTimeMills())
                                apply()
                            }
                        }
                    } else {
                        timerViewModel.addPauseTimeMills()
                        if (dataStore != null) {
                            with(dataStore.edit()) {
                                putInt("sumPauseTimeMills", timerViewModel.getSumPauseTimeMills())
                                apply()
                            }
                        }
                    }
                    handler.post(runnable)
                }
            }
        }

        Log.d("restart3", restartButton.isClickable.toString())

//        restartButton.setOnClickListener {
//            Log.d("restart4", restartButton.isClickable.toString())
//            isStartFirst = true
//            timerViewModel.init()
//            if (dataStore != null) {
//                with(dataStore.edit()) {
//                    putBoolean("isStartFirst", false) // Set SharedPreferences "isStarFirst"
//                    putInt("sumPauseTimeMills", 0) // Set SharePreferences "sumPauseTimeMills"
//                    apply()
//                }
//            }
//            timeCountTextView.text = "00:00:00:000"
//        }
        Log.d("restart5", restartButton.isClickable.toString())

        timerViewModel.timeCountTextViewLiveData.observe(viewLifecycleOwner){
            timeCountTextView.text = it
        }
    }
}
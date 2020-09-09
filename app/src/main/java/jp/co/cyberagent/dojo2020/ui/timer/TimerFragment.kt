package jp.co.cyberagent.dojo2020.ui.timer

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.ui.list.MemoListViewModel
import kotlinx.android.synthetic.main.timer_tab.*
import java.text.SimpleDateFormat
import java.util.*


class TimerFragment: Fragment(){


    private val handler = Handler()

    private var delay: Long = 0
    private var period:kotlin.Long = 0

    private var timeValue = 0                              // 秒カウンター

    private val dataFormat = SimpleDateFormat("mm:ss:SS", Locale.getDefault())

    private var tappedStartButtonFlag: Int = 0

    private var stopTimerViewFlag: Int = 0

    private val timerViewModel: TimerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        delay = 0;
        period = 100;

        // Handler(スレット間通信：イベントキュー？)
        val runnable = object : Runnable {
            override fun run() {
                if (stopTimerViewFlag == 0){
                    timerViewModel.timeCountTextViewLiveData.observe(viewLifecycleOwner){
                        timeCountTextView.text = it
                    }
                    handler.postDelayed(this, 1000)  // 1000ｍｓ後に自分にpost
                    Log.d("as","hello")
                }
            }
        }

        val startButton = view.findViewById<ImageButton>(R.id.startButton);
        val stopButton = view.findViewById<ImageButton>(R.id.stopButton);

        val timerText = view.findViewById<TextView>(R.id.timeCountTextView);
        val tempTextView = view.findViewById<TextView>(R.id.tempTextView);

        timeCountTextView.setText(dataFormat.format(0));

        startButton.setOnClickListener{
            if (tappedStartButtonFlag == 0){
                handler.post(runnable)
                tappedStartButtonFlag = 1
            }
            timerViewModel.changeToHello()
            timerViewModel.applyMutableTimeCountTextViewLiveData()
        }

        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            if (tappedStartButtonFlag == 1){
                startButton.setBackgroundResource(R.drawable.restart_icon)
            }
            tappedStartButtonFlag = 0
        }
    }

    override fun onStop() {
        super.onStop()
        stopTimerViewFlag = 1
    }

    override fun onStart() {
        super.onStart()
        timerViewModel.tempTextViewLiveData.observe(viewLifecycleOwner){
            tempTextView.text = it
        }
    }

}
package jp.co.cyberagent.dojo2020.ui.timer

import android.os.Bundle
import android.os.Handler
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
    private  var period:kotlin.Long = 0

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
                    timeValue++                      // 秒カウンタ+1
                    timeToText(timeValue)?.let {        // timeToText()で表示データを作り
                        timeCountTextView.text = it            // timeText.textへ代入(表示)
                    }
                    handler.postDelayed(this, 1000)  // 1000ｍｓ後に自分にpost
                }
            }
        }

        val startButton = view.findViewById<ImageButton>(R.id.startButton);
        val stopButton = view.findViewById<ImageButton>(R.id.stopButton);

        val timerText = view.findViewById<TextView>(R.id.timeCountTextView);
        val tempTextView = view.findViewById<TextView>(R.id.tempTextView);

        timerText.setText(dataFormat.format(0));

        startButton.setOnClickListener{
            if (tappedStartButtonFlag == 0){
                handler.post(runnable)
                tappedStartButtonFlag = 1
            }
            timerViewModel.changeToHello()
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

    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null                                    // 時刻が0未満の場合 null
        } else if (time == 0) {
            "00:00:00"                            // ０なら
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)  // 表示に整形
        }
    }

}
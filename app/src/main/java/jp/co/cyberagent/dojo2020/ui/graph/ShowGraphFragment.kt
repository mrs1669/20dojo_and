package jp.co.cyberagent.dojo2020.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import jp.co.cyberagent.dojo2020.databinding.ShowFragmentBinding

class ShowGraphFragment  : Fragment() {
    private lateinit var binding:ShowFragmentBinding
    private val graphViewModel by viewModels<GraphViewModel> {
        GraphViewModelFactory(
            requireContext()

        )
    }

    private val args: ShowGraphFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShowFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = graphViewModel

        val anyChartView: AnyChartView = binding.pieChartView
        val data = ArrayList<DataEntry>()



        val tag1 = args.ShowGraph?.times?.get(0)?.tag ?:""
        val dataGraph1 = args.ShowGraph?.times?.get(0)?.time ?: 1
        data.add(ValueDataEntry(tag1, dataGraph1))


        val tag2 = args.ShowGraph?.times?.get(1)?.tag ?: ""
        val dataGraph2 = args.ShowGraph?.times?.get(1)?.time ?: 1
        data.add(ValueDataEntry(tag2, dataGraph2))


        val tag3 = args.ShowGraph?.times?.get(2)?.tag ?:""
        val dataGraph3 = args.ShowGraph?.times?.get(2)?.time ?: 1
        data.add(ValueDataEntry(tag3, dataGraph3))

        val tag4 = args.ShowGraph?.times?.get(3)?.tag ?:""
        val dataGraph4 = args.ShowGraph?.times?.get(3)?.time ?: 1
        data.add(ValueDataEntry(tag4, dataGraph4))
        val pie = AnyChart.pie()
        pie.data(data)

        anyChartView.setChart(pie)
        return binding.root
    }

}

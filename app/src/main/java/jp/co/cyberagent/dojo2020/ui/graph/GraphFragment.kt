package jp.co.cyberagent.dojo2020.ui.graph

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import jp.co.cyberagent.dojo2020.databinding.GraphFragmentBinding
import jp.co.cyberagent.dojo2020.ui.list.InputMemoFragmentArgs


class GraphFragment : Fragment() {
    private lateinit var binding: GraphFragmentBinding
    private val graphViewModel by viewModels<GraphViewModel> {
        GraphViewModelFactory(
            requireContext()

        )
    }





//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        graphViewModel.data1.observe(this, Observer {
//            val tag = binding.viewModel?.tagName1?.value
//            val dataGraph = binding.viewModel?.data1?.value
//            data.add(ValueDataEntry(tag, dataGraph))
//        })
//        graphViewModel.data2.observe(this, Observer {
//            val tag = binding.viewModel?.tagName2?.value
//            val dataGraph = binding.viewModel?.data2?.value
//            data.add(ValueDataEntry(tag, dataGraph))
//        })
//        graphViewModel.data3.observe(this, Observer {
//            val tag = binding.viewModel?.tagName3?.value
//            val dataGraph = binding.viewModel?.data3?.value
//            data.add(ValueDataEntry(tag, dataGraph))
//        })
//        graphViewModel.data4.observe(this, Observer {
//
//        })
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GraphFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = graphViewModel


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showGraphButton = binding.btShowGraph
        showGraphButton.setOnClickListener{
            val tag1 = binding.spInputMemoTag.selectedItem.toString()
            val tag2 = binding.spInputMemoTag2.selectedItem.toString()
            val tag3 = binding.spInputMemoTag3.selectedItem.toString()
            val tag4 = binding.spInputMemoTag4.selectedItem.toString()
            binding.viewModel?.setGraphData(tag1, tag2, tag3, tag4, view)
        }
    }
}





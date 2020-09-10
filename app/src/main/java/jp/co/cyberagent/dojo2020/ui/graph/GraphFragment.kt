package jp.co.cyberagent.dojo2020.ui.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.databinding.FragmentInputMemoBinding
import jp.co.cyberagent.dojo2020.databinding.GraphFragmentBinding
import jp.co.cyberagent.dojo2020.ui.list.MemoListViewModel
import jp.co.cyberagent.dojo2020.ui.list.MemoListViewModelFactory


class GraphFragment : Fragment() {
    private lateinit var binding: GraphFragmentBinding
    private val graphViewModel by viewModels<GraphViewModel> {
        GraphModelFactory(
            requireContext()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GraphFragmentBinding.inflate(inflater,container,false)
        binding.viewModel = graphViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    companion object {

    }
}
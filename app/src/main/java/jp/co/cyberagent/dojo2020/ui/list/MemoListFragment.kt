package jp.co.cyberagent.dojo2020.ui.list

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.databinding.MemoListTabBinding
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.ui.RecyclerMemoAdapter
import kotlinx.android.synthetic.main.recyclerview_memo_item.*

class MemoListFragment() : Fragment() {

    private lateinit var binding: MemoListTabBinding
    private val memoListViewModel by viewModels<MemoListViewModel> {
        MemoListViewModelFactory(
            this,
            Bundle(),
            this.requireContext()
        )
    }

    lateinit var adapter: RecyclerMemoAdapter
    lateinit var manager: FragmentManager





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MemoListTabBinding.inflate(layoutInflater)

        binding.viewModel = memoListViewModel
        val layout = LinearLayoutManager(context)
        binding.memoRecyclerView.layoutManager = layout
        binding.memoRecyclerView.adapter = RecyclerMemoAdapter(listener = listener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manager = activity?.supportFragmentManager!!



        memoListViewModel.memoMutableList.observe(viewLifecycleOwner, Observer {
            val adapter = binding.memoRecyclerView.adapter as RecyclerMemoAdapter?
            adapter?.setMemo(it)
        })
        this.binding = binding

        binding.lifecycleOwner = this.viewLifecycleOwner


        binding.floatingActionButton.setOnClickListener {
            val memo = Memo(0,"",0,0,"")
            val action = MemoListFragmentDirections.actionNaviMemoListToInputMemoData(memo)
            view.findNavController().navigate(action)
        }


    }

    val listener = object : RecyclerMemoAdapter.Listener {
        override fun onClickItem(memo: Memo) {
            memoListViewModel.deleteMemo(memo)
        }

        override fun onClickEditButton(memo: Memo) {
            val action = MemoListFragmentDirections.actionNaviMemoListToInputMemoData(memo)
            view!!.findNavController().navigate(action)
        }

        override fun getFragmentManager() = manager
    }
}
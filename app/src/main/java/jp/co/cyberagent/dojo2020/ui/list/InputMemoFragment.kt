package jp.co.cyberagent.dojo2020.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.databinding.FragmentInputMemoBinding
import jp.co.cyberagent.dojo2020.models.Memo

class InputMemoFragment : Fragment() {
    private val args: InputMemoFragmentArgs by navArgs()

    private val memoListViewModel by viewModels<MemoListViewModel> {
        MemoListViewModelFactory(
            this,
            Bundle(),
            this.requireContext()
        )
    }

    private lateinit var binding: FragmentInputMemoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInputMemoBinding.inflate(inflater,container,false)
        binding.viewModel = memoListViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memoListViewModel.editMemo.value=args.editMemoData

        val submitButton = view.findViewById<Button>(R.id.btInputMemoSubmit)
        submitButton.setOnClickListener {
            val title = view.findViewById<EditText>(R.id.etInputMemoTitle).text.toString()
            val hour = view.findViewById<EditText>(R.id.etInputMemoHour).text.toString().toInt()
            val minute = view.findViewById<EditText>(R.id.etInputMemoMinute).text.toString().toInt()
            val description = view.findViewById<EditText>(R.id.etDescription).text.toString()
            if (title == null || (hour == 0 && minute == 0) || description == null || minute > 59) {
                Toast.makeText(context, "入力情報が不適切です", Toast.LENGTH_LONG).show()
            } else {
                var id = memoListViewModel.editMemo.value!!.id
                val memo = Memo(id , title, hour.toInt(), minute.toInt(), description)
                when(id) {
                    0 -> memoListViewModel.saveMemo(memo)
                    else -> memoListViewModel.updateMemo(memo)
                }
                memoListViewModel.editMemo.value = Memo(0, "", 0,0,"")
                findNavController().navigate(R.id.action_input_memo_data_to_navi_memo_list)
            }
        }
    }
}
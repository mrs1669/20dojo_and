package jp.co.cyberagent.dojo2020.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.databinding.FragmentInputMemoBinding
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.models.Tag

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

//        memoListViewModel.tagMutableList.observe(viewLifecycleOwner, Observer {
//            val tags = binding.viewModel.tagMutableList as List<String>
//            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tags)
//            binding.spInputMemoTag.adapter = spinnerAdapter
//        })

        val submitButton = binding.btInputMemoSubmit
        submitButton.setOnClickListener {
            val title = binding.etInputMemoTitle.text.toString()
            val hour = binding.etInputMemoHour.text.toString().toInt()
            val minute = binding.etInputMemoMinute.text.toString().toInt()
            val description = binding.etDescription.text.toString()
            val tag = binding.spInputMemoTag.selectedItem.toString()
            val url = binding.etUrl.text.toString()
            Log.d("tag in inputFragment", tag)

            if (title == null || (hour == 0 && minute == 0) || description == null || minute > 59 || tag == "タグが登録されていません" || tag == null) {
                Toast.makeText(context, "入力情報が不適切です", Toast.LENGTH_LONG).show()
            } else {
                var id = memoListViewModel.editMemo.value!!.id
                val memo = Memo(id ,tag, title, hour.toInt(), minute.toInt(), description,url)
                when(id) {
                    0 -> memoListViewModel.saveMemo(memo)
                    else -> memoListViewModel.updateMemo(memo)
                }
                memoListViewModel.editMemo.value = Memo(0 ,"","", 0,0,"","")
                findNavController().navigate(R.id.action_input_memo_data_to_navi_memo_list)
        }
        }

//        var adapter: ArrayAdapter<LiveData<List<String>>> = ArrayAdapter<>(
//            this,
//            android.R.layout.simple_spinner_item,
//            binding.viewModel.tagMutableList
//        )



        binding.btTagAdd.setOnClickListener{
            val tagName = binding.etTagAdd.text.toString()
            if (tagName == null || tagName == "" ) {
                Toast.makeText(context, "入力情報が不適切です", Toast.LENGTH_LONG).show()
            } else {
                memoListViewModel.saveTag(Tag(tagName))
                binding.etTagAdd.setText("")
            }
        }

        binding.btBuckToList.setOnClickListener{
            findNavController().navigate(R.id.action_input_memo_data_to_navi_memo_list)
        }


    }
}

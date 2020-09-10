package jp.co.cyberagent.dojo2020.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.databinding.RecyclerviewMemoItemBinding
import jp.co.cyberagent.dojo2020.models.Memo
import kotlinx.android.synthetic.main.recyclerview_memo_item.view.*

class RecyclerMemoAdapter(
    initList: List<Memo> = emptyList(),
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerMemoAdapter.RecyclerViewHolder>() {

    var memoList: List<Memo> = initList



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RecyclerviewMemoItemBinding = DataBindingUtil.inflate<RecyclerviewMemoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_memo_item,
            parent,
            false
        )
        return RecyclerViewHolder(binding)
    }

    class RecyclerViewHolder(val binding: RecyclerviewMemoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val memo = memoList[position]
        val dialog: DialogViewFragment = DialogViewFragment(memo, listener)
        holder.binding.viewModel = Memo(memo.id,memo.title,memo.hour,memo.minute,memo.description)

        holder.itemView.ll_card_all.setOnClickListener{
            listener.getFragmentManager()?.let { manager -> dialog.show(manager, "sample") }
        }

//        holder.itemView.btDeleteMemo.setOnClickListener{
//            listener.onClickItem(memo)
//        }
//
//        holder.itemView.btEditMemo.setOnClickListener{
//            listener.onClickEditButton(memo)
//        }
    }

    fun setMemo(memo: List<Memo>) {
        this.memoList = memo
        notifyDataSetChanged()
    }

    inner private class MemoItemClickListener(position: Int) : View.OnClickListener {
        val position : Int = position
        override fun onClick(view: View) {
            Log.i("MemoItemClickListener", "$position")
            listener
//            holder.
        }
    }

    override fun getItemCount(): Int = memoList.size

    interface  Listener {
        fun onClickItem(memo:Memo)
        fun onClickEditButton(memo: Memo)
        fun getFragmentManager(): FragmentManager?
    }
}
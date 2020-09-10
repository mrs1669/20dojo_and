package jp.co.cyberagent.dojo2020.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import jp.co.cyberagent.dojo2020.models.Memo

class DialogViewFragment(val memo: Memo, val listener: RecyclerMemoAdapter.Listener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        builder.setTitle("メモをどうしますか？")
            .setPositiveButton("削除", {dialog, which ->

                listener.onClickItem(memo)

            })
            .setNegativeButton("編集", {dialog, which ->
                listener.onClickEditButton(memo)

            })
            .setNeutralButton("戻る", {dialog, which ->

            })

        return builder.create()
    }

}
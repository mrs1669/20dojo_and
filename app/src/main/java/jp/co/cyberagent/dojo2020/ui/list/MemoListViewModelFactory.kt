package jp.co.cyberagent.dojo2020.ui.list

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

/**
 * SavedStateHandleは、OSがActivityとかをKillしたときにデータを復元する際に使うんやけど、今回は特にいらなさそうかも？
 * （自分もSavedStateRegistryOwnerあたりとか理解していないw）
 * 普通にやるなら、ViewModelProvider.Factoryを継承するやり方が良いかな
 * 下の感じ！
 * 参考：https://medium.com/url-memo/viewmodel-viewmodelprovider-c95a19412805
 */
//@Suppress("UNCHECKED_CAST")
//class MemoListViewModelFactory (
//    private val context: Context
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        when (modelClass) {
//            MemoListViewModel::class.java -> return MemoListViewModel(context) as T
//            else -> throw RuntimeException("Cannot create an instance of $modelClass")
//        }
//    }
//}

@Suppress("UNCHECKED_CAST")
class MemoListViewModelFactory (
    owner: SavedStateRegistryOwner,
    defaultArgs : Bundle,
    private val context: Context
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MemoListViewModel(context) as T
    }
}

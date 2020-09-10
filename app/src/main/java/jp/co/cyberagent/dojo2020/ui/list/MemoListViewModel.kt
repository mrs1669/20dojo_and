package jp.co.cyberagent.dojo2020.ui.list

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import jp.co.cyberagent.dojo2020.DI
import jp.co.cyberagent.dojo2020.data.Repository
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoListViewModel(context: Context): ViewModel() {
    private val memoRepository: Repository
    val memoMutableList : LiveData<List<Memo>>
    val tagMutableList: LiveData<List<String>>
    val editMemo : MutableLiveData<Memo> by lazy {
        MutableLiveData<Memo>()
    }
    var selectedtag : String

    init {
        memoRepository = DI.injectRepository(context)
        memoMutableList = memoRepository.loadAllMemo()
        tagMutableList = memoRepository.loadAllTag().map {
            it.map {
                it.tag
            }
        }
        selectedtag = tagMutableList.value?.get(0) ?: "タグが登録されていません"
    }

    fun saveMemo(memo: Memo) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.inputMemo(memo)
        Log.i("test: in ViewModel ", memo.title )
    }

    fun loadAllMemo() {
        viewModelScope.launch {
            val memoData = memoRepository.loadAllMemo()
        }
    }

    fun saveTag(tag: Tag) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.inputTag(tag)
        Log.i("test: in ViewModel ", tag.tag )
    }

    fun loadAllTag() {
        viewModelScope.launch {
            memoRepository.loadAllTag().map {
                it.map {
                    it.tag
                }
            }
        }
    }

    fun deleteMemo(memo: Memo) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.deleteMemo(memo)
    }

    fun updateMemo(memo: Memo) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.updateMemo(memo)
    }
}
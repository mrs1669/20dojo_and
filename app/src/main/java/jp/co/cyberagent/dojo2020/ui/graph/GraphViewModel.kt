package jp.co.cyberagent.dojo2020.ui.graph

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import jp.co.cyberagent.dojo2020.DI
import jp.co.cyberagent.dojo2020.data.Repository
import jp.co.cyberagent.dojo2020.models.GraphArraay
import jp.co.cyberagent.dojo2020.models.GraphData
import jp.co.cyberagent.dojo2020.models.Memo
import jp.co.cyberagent.dojo2020.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GraphViewModel(context: Context): ViewModel() {
    private val memoRepository: Repository
    val memoMutableList : LiveData<List<Memo>>
    val tagMutableList: LiveData<List<String>>
    var graphDataArray: GraphArraay
    var selectedtag : String
    fun getGraphValue(tag: String, memoArray: LiveData<List<Memo>> ) : Int{
        var sum = 0
        viewModelScope.launch (Dispatchers.IO){

            Log.i("getGraphValue:sum", sum.toString())
            memoArray.map {
                it.forEach{
                    if (it.tag == tag){
                        sum += it.hour * 60 + it.minute
                    }
                    viewModelScope.launch (Dispatchers.IO) {
                        Log.i("getGraphValue tag", it.tag)
                        Log.i("getGraphValue", sum.toString())
                    }

                }

            }
        }

        return sum
    }
    fun getArrayGraphData (memoMutableList : LiveData<List<Memo>>, tagMutableList: LiveData<List<String>>): GraphArraay{
        var graphDataArray = GraphArraay(mutableListOf<GraphData>())
        tagMutableList.value?.forEach{
            graphDataArray.graphDataArray.add(GraphData(it,getGraphValue(it, memoMutableList)))
        }
        viewModelScope.launch (Dispatchers.IO) {
            Log.i("getArrayGraphData", graphDataArray.graphDataArray.toString())
        }

        return graphDataArray
    }
    init {
        memoRepository = DI.injectRepository(context)
        memoMutableList = memoRepository.loadAllMemo()
        tagMutableList = memoRepository.loadAllTag().map {
            it.map {
                it.tag
            }
        }
        selectedtag = tagMutableList.value?.get(0) ?: "タグが登録されていません"
        graphDataArray = getArrayGraphData(memoMutableList, tagMutableList)
    }


    fun loadAllMemo() {
        viewModelScope.launch {
            val memoData = memoRepository.loadAllMemo()
        }
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
}
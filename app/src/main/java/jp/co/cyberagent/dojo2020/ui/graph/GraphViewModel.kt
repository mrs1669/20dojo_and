package jp.co.cyberagent.dojo2020.ui.graph

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import jp.co.cyberagent.dojo2020.DI
import jp.co.cyberagent.dojo2020.data.Repository
import jp.co.cyberagent.dojo2020.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GraphViewModel(
    context: Context
): ViewModel() {
    private val memoRepository: Repository
    val memoMutableList : LiveData<List<Memo>>
    val tagMutableList: LiveData<List<String>>
    var data1: MutableLiveData<Int> =  MutableLiveData<Int>(1)
    var data2: MutableLiveData<Int> =  MutableLiveData<Int>(1)
    var data3: MutableLiveData<Int> =  MutableLiveData<Int>(1)
    var data4: MutableLiveData<Int> =  MutableLiveData<Int>(1)
    var tagName1: MutableLiveData<String> =  MutableLiveData<String>("")
    var tagName2: MutableLiveData<String> =  MutableLiveData<String>("")
    var tagName3: MutableLiveData<String> =  MutableLiveData<String>("")
    var tagName4: MutableLiveData<String> =  MutableLiveData<String>("")
//    var graphDataArray: LiveData<GraphArraay> = LiveData<GraphArraay>(GraphArraay)
    var selectedtag : String
//    suspend fun getGraphValue(tag: String) : Int{
//        viewModelScope.launch {
//            val reslut =
//        }
//    }
//    fun getArrayGraphData (tagMutableList: LiveData<List<String>>): GraphArraay{
//        var graphDataArray = GraphArraay(mutableListOf<GraphData>())
//        Log.i("test","test")
//        Log.i("test", tagMutableList.value?.get(0))
//        tagMutableList.map{
//            println(it)
//            it.forEach{
//                Log.i("selectGraphData in vm", it)
//                graphDataArray.graphDataArray.add( GraphData(it,getGraphValue(it)))
//            }
//        }
//        viewModelScope.launch {
//            Log.i("getArrayGraphData", graphDataArray.graphDataArray.toString())
//        }
//
//        return graphDataArray
//    }
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

    fun setGraphData (tag1:String,tag2:String,tag3:String,tag4:String , view: View){
        tagName1.value=tag1
        tagName2.value=tag2
        tagName3.value=tag3
        tagName4.value=tag4

        var tagName2: MutableLiveData<String> =  MutableLiveData<String>()
        var tagName3: MutableLiveData<String> =  MutableLiveData<String>()
        var tagName4: MutableLiveData<String> =  MutableLiveData<String>()
        viewModelScope.launch {
             val result1 = async(Dispatchers.IO) {
                 var sum = 0
                 val times = memoRepository.selectGraphData(tag1)

                 times.forEach{
                     sum += it.hour * 60 + it.minute
                 }
                 sum
             }
            data1.value = result1.await()
            val result2 = async(Dispatchers.IO) {
                var sum = 0
                val times = memoRepository.selectGraphData(tag2)
                times.forEach{
                    sum += it.hour * 60 + it.minute
                }
                sum
            }
            data2.value = result2.await()
            val result3 = async(Dispatchers.IO) {
                var sum = 0
                val times = memoRepository.selectGraphData(tag3)
                times.forEach{
                    sum += it.hour * 60 + it.minute
                }
                sum
            }
            data3.value = result3.await()
            val result4 = async(Dispatchers.IO) {
                var sum = 0
                val times = memoRepository.selectGraphData(tag4)
                times.forEach{
                    sum += it.hour * 60 + it.minute
                }
                sum
            }
            data4.value = result4.await()
            var data_graph:Times =  Times(mutableListOf<GraphData>(GraphData(data1.value?: 0,tag1), GraphData(data2.value?: 0,tag2),GraphData(data3.value?: 0,tag3),GraphData(data4.value?: 0,tag4)))
            val action = GraphFragmentDirections.actionDataGraphToNavShowGraph(data_graph)
            view.findNavController().navigate(action)
        }
    }

}


package jp.co.cyberagent.dojo2020.ui.graph

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner

@Suppress("UNCHECKED_CAST")
class GraphModelFactory (
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T {
        when(modelClass) {
            GraphViewModel::class.java -> return GraphViewModel(context) as T
            else -> throw RuntimeException("Cannot create an instance of $modelClass")
        }
    }
}
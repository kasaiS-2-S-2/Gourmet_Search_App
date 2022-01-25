package com.kasai.gourmet_search_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kasai.gourmet_search_app.R


class SearchGourmetViewModel(application: Application) : AndroidViewModel(application) {

    val range: MutableLiveData<Int> =
        MutableLiveData<Int>().also { mutableLiveData ->
            mutableLiveData.value = 0
        }

    fun judgeRange(id: Int): Int {
        return when(id) {
            R.id.range1 -> 1
            R.id.range2 -> 2
            R.id.range3 -> 3
            R.id.range4 -> 4
            R.id.range5 -> 5
            else -> 0
        }
    }
}
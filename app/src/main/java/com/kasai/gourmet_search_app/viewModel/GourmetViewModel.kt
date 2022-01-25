package com.kasai.gourmet_search_app.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.kasai.gourmet_search_app.service.model.Gourmet
import com.kasai.gourmet_search_app.service.repository.GourmetRepository
import kotlinx.coroutines.launch

class GourmetViewModel(
        private val myApplication: Application,
        private val mKey: String,
        private val mGourmetID: String,
        private val mFormat: String
) : AndroidViewModel(myApplication) {

    private val repository = GourmetRepository.instance
    val gourmetLiveData: MutableLiveData<Gourmet.Results.Shop> = MutableLiveData()

    var gourmet = ObservableField<Gourmet.Results.Shop>()

    init {
        loadGourmet()
    }

    private fun loadGourmet() = viewModelScope.launch {
        try {
            val gourmet = repository.getGourmetDetails(mKey, mGourmetID, mFormat)
            if (gourmet.isSuccessful) {
                gourmetLiveData.postValue(gourmet.body()?.results?.shop?.get(0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setGourmet(gourmet: Gourmet.Results.Shop) {
        this.gourmet.set(gourmet)
    }

    class Factory(private val application: Application,
                  private val key: String,
                  private val gourmetID: String,
                  private val format: String) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GourmetViewModel(application, key, gourmetID, format) as T
        }
    }
}

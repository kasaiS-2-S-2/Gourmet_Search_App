package com.kasai.gourmet_search_app.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.kasai.gourmet_search_app.service.model.Gourmet
import com.kasai.gourmet_search_app.service.repository.GourmetRepository
import kotlinx.coroutines.launch

class GourmetListViewModel(
    private val myApplication: Application,
    private val mKey: String,
    private val mLat: String,
    private val mLng: String,
    private val mRange: String,
    private val mFormat: String
) : AndroidViewModel(myApplication) {

    private val repository = GourmetRepository.instance
    var gourmetListLiveData: MutableLiveData<Gourmet.Results> = MutableLiveData()

    init {
        loadGourmetList()
    }

    private fun loadGourmetList() = viewModelScope.launch {
        try {
            val response = repository.getGourmetList(mKey, mLat, mLng, mRange, mFormat)
            if (response.isSuccessful) {
                gourmetListLiveData.postValue(response.body()?.results)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class Factory(private val application: Application,
                  private val key: String,
                  private val lat: String,
                  private val lng: String,
                  private val range: String,
                  private val format: String) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GourmetListViewModel(application, key, lat, lng, range, format) as T
        }
    }
}

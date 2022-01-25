package com.kasai.gourmet_search_app.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kasai.gourmet_search_app.R
import com.kasai.gourmet_search_app.databinding.FragmentGourmetListBinding
import com.kasai.gourmet_search_app.service.model.Gourmet
import com.kasai.gourmet_search_app.view.adapter.GourmetAdapter
import com.kasai.gourmet_search_app.view.callback.GourmetClickCallback
import com.kasai.gourmet_search_app.viewModel.GourmetListViewModel

class GourmetListFragment : Fragment() {

    companion object {
        private const val API_KEY = "api_key"
        private const val LAT = "lat"
        private const val LNG = "lng"
        private const val RANGE = "range"
        private const val FORMAT = "format"

        fun forGourmetList(key: String, lat: String, lng: String, range: String, format: String) = GourmetListFragment().apply {
            arguments = Bundle().apply {
                putString(API_KEY, key)
                putString(LAT, lat)
                putString(LNG, lng)
                putString(RANGE, range)
                putString(FORMAT, format)
            }
        }
    }

    private val key by lazy { requireNotNull(arguments?.getString(API_KEY)) }
    private val lat by lazy { requireNotNull(arguments?.getString(LAT)) }
    private val lng by lazy { requireNotNull(arguments?.getString(LNG)) }
    private val range by lazy { requireNotNull(arguments?.getString(RANGE)) }
    private val format by lazy { requireNotNull(arguments?.getString(FORMAT)) }

    private val viewModel by lazy {
        ViewModelProvider(this, GourmetListViewModel.Factory(
            requireActivity().application, key, lat, lng, range, format
        )).get(GourmetListViewModel::class.java)
    }

    private lateinit var binding: FragmentGourmetListBinding
    private val gourmetAdapter: GourmetAdapter = GourmetAdapter(object : GourmetClickCallback {
        override fun onClick(gourmet: Gourmet.Results.Shop) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                (activity as MainActivity).showGourmet(getString(R.string.gourmet_api_key), gourmet, getString(R.string.search_data_format))
            }
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gourmet_list, container, false)
        binding.apply {
            gourmetList.adapter = gourmetAdapter
            isLoading = true
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.gourmetListLiveData.observe(viewLifecycleOwner, Observer { gourmetList ->
            gourmetList?.let {
                binding.isLoading = false
                gourmetAdapter.setGourmetList(it)
            }
        })
    }
}

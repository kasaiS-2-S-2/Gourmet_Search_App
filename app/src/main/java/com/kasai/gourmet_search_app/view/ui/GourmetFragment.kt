package com.kasai.gourmet_search_app.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kasai.gourmet_search_app.R
import com.kasai.gourmet_search_app.databinding.FragmentGourmetDetailsBinding
import com.kasai.gourmet_search_app.viewModel.GourmetViewModel

class GourmetFragment : Fragment() {

    companion object {
        private const val KEY_GOURMET_ID = "gourmet_id"

        fun forGourmet(gourmetId: String) = GourmetFragment().apply {
            arguments = Bundle().apply { putString(KEY_GOURMET_ID, gourmetId) }
        }
    }

    private val gourmetId by lazy { requireNotNull(arguments?.getString(KEY_GOURMET_ID)) }

    private val viewModel by lazy {
        ViewModelProvider(this, GourmetViewModel.Factory(
                requireActivity().application, gourmetId
        )).get(GourmetViewModel::class.java)
    }

    private lateinit var binding: FragmentGourmetDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gourmet_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            gourmetViewModel = viewModel
            isLoading = true
        }

        viewModel.gourmetLiveData.observe(viewLifecycleOwner, Observer { gourmet ->
            gourmet?.let {
                binding.isLoading = false
                viewModel.setGourmet(it)
            }
        })
    }
}

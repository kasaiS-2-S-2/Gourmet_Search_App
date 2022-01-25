package com.kasai.gourmet_search_app.view.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kasai.gourmet_search_app.R
import com.kasai.gourmet_search_app.databinding.FragmentSearchGourmetBinding
import com.kasai.gourmet_search_app.viewModel.SearchGourmetViewModel


const val TAG_OF_SEARCH_GOURMET_FRAGMENT = "SearchGourmetFragment"

class SearchGourmetFragment : Fragment() {

    private lateinit var binding: FragmentSearchGourmetBinding

    private val requestPermissionLauncher = getRequestPermissionLauncher()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel by lazy {
        ViewModelProvider(this).get(SearchGourmetViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSearchGourmetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchGourmetViewModel = viewModel
            searchGourmetFragment = this@SearchGourmetFragment
        }
    }

    fun onSearchButtonPressed() {
        viewModel.range
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            searchGourmetList()
        }
    }

    private fun getRequestPermissionLauncher(): ActivityResultLauncher<String> {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    searchGourmetList()
                } else {
                    val toast: Toast = Toast.makeText(activity, R.string.current_place_permisstion_denied_meg, Toast.LENGTH_LONG)
                    toast.show()
                }
            }

        return requestPermissionLauncher
    }

    private fun searchGourmetList() {
        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (gpsEnabled) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                // GPSから位置情報を取得
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val lat = location.latitude
                        val lng = location.longitude
                        if (viewModel.range.value != null && viewModel.range.value != 0) {
                            val range = viewModel.judgeRange(viewModel.range.value!!)
                            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                                (activity as MainActivity).showGourmetList(
                                    getString(R.string.gourmet_api_key),
                                    lat.toString(),
                                    lng.toString(),
                                    range.toString(),
                                    getString(R.string.search_data_format)
                                )
                            }
                        } else {
                            val toast: Toast = Toast.makeText(activity,
                                R.string.set_search_range_meg,
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    } else {
                        val toast: Toast = Toast.makeText(activity,
                            R.string.cannot_get_current_place_meg,
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            } else {
                val toast: Toast = Toast.makeText(activity, R.string.current_place_permisstion_denied_meg, Toast.LENGTH_LONG)
                toast.show()
            }
        } else {
            val toast: Toast = Toast.makeText(
                activity,
                R.string.turn_on_current_place,
                Toast.LENGTH_LONG
            )
            toast.show()
        }
    }
}
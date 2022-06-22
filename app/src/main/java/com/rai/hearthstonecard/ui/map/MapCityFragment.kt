package com.rai.hearthstonecard.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rai.hearthstonecard.databinding.FragmentMapCityBinding
import com.rai.hearthstonecard.domain.model.LceState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapCityFragment : Fragment() {
    private var _binding: FragmentMapCityBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val viewModel by viewModel<MapCityViewModel>()


    private var googleMap: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    @SuppressLint("MissingPermission")
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.locationService.getCurrentLocation() ?: let { ::moveCameraToLocation }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentMapCityBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        viewModel.locationService.getLocationFlow().onEach {
            locationListener?.onLocationChanged(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.mapView.getMapAsync { map ->
            googleMap = map.apply {
                uiSettings.isCompassEnabled = true
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            map.isMyLocationEnabled = hasLocationPermission()
            map.setLocationSource(object : LocationSource {
                override fun activate(listener: LocationSource.OnLocationChangedListener) {
                    locationListener = listener
                }
                override fun deactivate() {
                    locationListener = null
                }
            })

           map.setOnMarkerClickListener { marker ->
                findNavController().navigate(MapCityFragmentDirections.actionMapsToMapInfoFragment( marker.tag.toString() ))
                true
            }
        }

        lifecycleScope.launch {
            viewModel.citiesFlow.collect { lce ->
                when (lce) {
                    is LceState.Content -> {
                        isVisibleProgressBar(false)
                        lce.data.forEach {
                            googleMap?.addMarker(
                                MarkerOptions()
                                    .title(it.name)
                                    .position(
                                        LatLng(it.latitude, it.longitude)
                                    )
                            )?.tag = it.id
                        }
                    }
                    is LceState.Error -> {
                        isVisibleProgressBar(false)
                        Toast.makeText(requireContext(),
                            lce.throwable.message ?: "", Toast.LENGTH_SHORT).show()
                    }
                    LceState.Loading -> {
                        isVisibleProgressBar(true)
                    }
                }
            }
        }


        binding.mapView.onCreate(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            googleMap?.setPadding(
                systemBarInsets.left,
                systemBarInsets.right,
                systemBarInsets.bottom,
                systemBarInsets.top
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
        googleMap = null
        _binding = null
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun moveCameraToLocation(location: Location) {
        val current = LatLng(location.latitude, location.longitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(current, 17f)
        )
    }

    private fun isVisibleProgressBar(visible:Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }



}
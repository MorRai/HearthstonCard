package com.rai.hearthstonecard.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rai.hearthstonecard.databinding.BottomSheetCityBinding
import com.rai.hearthstonecard.domain.model.City
import com.rai.hearthstonecard.domain.model.LceState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MapInfoFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCityBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val viewModel by viewModel<MapInfoViewModel> {
        parametersOf(args.id)
    }

    private val args by navArgs<MapInfoFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return BottomSheetCityBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.cityFlow.collect { lce ->
                when (lce) {
                    is LceState.Content -> {
                        bind(lce.data)
                    }
                    is LceState.Error -> {
                        Toast.makeText(requireContext(),
                            lce.throwable.message ?: "", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun bind(city: City) {
        with(binding) {
            cityName.text = city.name
            longitude.text = city.longitude.toString()
            latitude.text = city.latitude.toString()
            region.text = city.region
            population.text = city.population.toString()
            country.text = city.country
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.rai.hearthstonecard.ui.classes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rai.hearthstonecard.adapter.ClassPersonAdapter
import com.rai.hearthstonecard.appComponent
import com.rai.hearthstonecard.databinding.FragmentListClassesBinding
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.ui.map.MapCityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class ClassPersonFragment : Fragment() {
    private var _binding: FragmentListClassesBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

   // private val viewModel by viewModel<PersonClassViewModel>()

    private val viewModel: PersonClassViewModel by viewModels {
        personClassViewModelFactory.create()
    }

    @Inject
    lateinit var personClassViewModelFactory: PersonClassViewModelFactory.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListClassesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val adapter =
                ClassPersonAdapter(requireContext()) {
                    findNavController().navigate(ClassPersonFragmentDirections.actionClassPersonFragmentToListCardFragment(
                        it))
                }
            val layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.classesFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                            adapter.submitList(lce.data)
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
        }
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.rai.hearthstonecard.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.adapter.ClassPersonAdapter
import com.rai.hearthstonecard.databinding.FragmentListClassesBinding
import com.rai.hearthstonecard.util.LceState
import com.rai.hearthstonecard.viewmodels.PersonClassViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassPersonFragment : Fragment() {
    private var _binding: FragmentListClassesBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<PersonClassViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListClassesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    @SuppressLint("ShowToast")
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

            lifecycleScope.launch {
                viewModel.classesFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            adapter.submitList(lce.data)
                        }
                        is LceState.Error -> {
                            Toast.makeText(requireContext(),
                                lce.throwable.message ?: "", Toast.LENGTH_SHORT).show()
                        }
                        LceState.Loading -> {
                            Toast.makeText(requireContext(),
                                getString(R.string.loading), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
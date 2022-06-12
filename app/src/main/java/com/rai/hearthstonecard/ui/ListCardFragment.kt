package com.rai.hearthstonecard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.adapter.CardAdapter
import com.rai.hearthstonecard.addCardDecoration
import com.rai.hearthstonecard.addPaginationScrollListener
import com.rai.hearthstonecard.databinding.FragmentListCardBinding
import com.rai.hearthstonecard.model.Item
import com.rai.hearthstonecard.util.LceState
import com.rai.hearthstonecard.viewmodels.ListCardViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ListCardFragment : Fragment() {

    private var _binding: FragmentListCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<ListCardFragmentArgs>()

    private val viewModel by viewModel<ListCardViewModel> {
        parametersOf(args.classPerson)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListCardBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setupWithNavController(findNavController())

            val adapter =
                CardAdapter(requireContext()) {
                    findNavController().navigate(ListCardFragmentDirections.actionListCardFragmentToDetailCardFragment(
                        it.data.id))
                }
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = false
            }
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addCardDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(layoutManager, ITEM_TO_LOAD) {
                viewModel.onLoadCards()
            }

            viewModel.cardsFlow.onEach { lce ->
                when (lce) {
                    is LceState.Content -> {
                        adapter.submitList(lce.data.map { Item.Content(it) } + Item.Loading)
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
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 25
        private const val ITEM_TO_LOAD = 20
    }

}


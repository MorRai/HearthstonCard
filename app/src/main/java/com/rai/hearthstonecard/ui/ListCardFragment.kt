package com.rai.hearthstonecard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rai.hearthstonecard.HearthstoneApplication
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.adapter.CardAdapter
import com.rai.hearthstonecard.addCardDecoration
import com.rai.hearthstonecard.databinding.FragmentListCardBinding
import com.rai.hearthstonecard.retrofit.Item
import com.rai.hearthstonecard.viewmodels.CardViewModel
import com.rai.hearthstonecard.viewmodels.CardViewModelFactory
import kotlinx.coroutines.launch


class ListCardFragment : Fragment() {

    private var _binding: FragmentListCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel: CardViewModel by activityViewModels {
        CardViewModelFactory(
            (requireActivity().application as HearthstoneApplication).cardRepository
        )
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
            recyclerView.addCardDecoration(SPACE_SIZE)//правда в данном случае не особо нужен
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val cards = viewModel.getAllCards(1).map { Item.Content(it) } + Item.Loading
                    adapter.submitList(cards)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(),
                        getString(R.string.error_getcards, e.message),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 25
        //private const val ITEM_TO_LOAD = 20 покуда не нужон
    }

}


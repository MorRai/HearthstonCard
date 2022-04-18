package com.rai.hearthstonecard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rai.hearthstonecard.adapter.CardAdapter
import com.rai.hearthstonecard.addCardDecoration
import com.rai.hearthstonecard.addPaginationScrollListener
import com.rai.hearthstonecard.databinding.FragmentListCardBinding
import com.rai.hearthstonecard.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListCardFragment : Fragment() {

    private var _binding: FragmentListCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private var currentCall: Call<Cards>? = null
    private var currentPage = 1

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
                    val action =
                        ListCardFragmentDirections.actionListCardFragmentToDetailCardFragment(it.id)
                    findNavController().navigate(action)
                }
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = false
            }
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addCardDecoration(SPACE_SIZE)//правда в данном случае не особо нужен
            recyclerView.addPaginationScrollListener(layoutManager, 20) {
                if (currentCall == null) {
                    swipeLayout.isRefreshing = true
                    getCurrentCall(adapter)
                }
                swipeLayout.isRefreshing = false
            }
            getCurrentCall(adapter)
        }
    }

    private fun getCurrentCall(adapter: CardAdapter) {
        currentCall = CardService.providerCardApi().getCards(currentPage)
        currentCall?.enqueue(object : Callback<Cards> {
            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                if (response.isSuccessful) {
                    val answer = response.body() ?: return
                    val currentList = adapter.currentList.toList().dropLast(1)
                    val resultList = currentList
                        .plus(
                            answer.cards
                        )
                        .plus(
                            CardItem.Loading
                        )
                    adapter.submitList(resultList)
                    currentPage++
                } else {
                    Toast.makeText(context,
                        "Failure while requesting cards",
                        Toast.LENGTH_LONG).show()
                }
                currentCall = null
            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                currentCall = null
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentCall?.cancel()
        currentPage = 1
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 25
    }

}


package com.rai.hearthstonecard.ui

import android.content.Context
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

    private val preferences by lazy {
        requireContext().getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
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
        getToken()
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
            recyclerView.addPaginationScrollListener(layoutManager, ITEM_TO_LOAD) {
                if (currentCall == null) {
                    swipeLayout.isRefreshing = true
                    getCurrentCall(adapter)
                }
                swipeLayout.isRefreshing = false
            }
            getCurrentCall(adapter)
        }
    }

    private fun getToken() {
        val tokenCall = AccessTokenService.tokenApi().getToken(CLIENT_ID, CLIENT_SECRET, GRANT_TYPE)
        tokenCall.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>,
            ) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body() ?: return
                    preferences
                        .edit()
                        .putString(USER_TOKEN, tokenResponse.accessToken)
                        .apply()
                } else {
                    Toast.makeText(context,
                        "Failure while requesting token",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(context,
                    "Failure while requesting token",
                    Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getCurrentCall(adapter: CardAdapter) {
        val token = preferences.getString(USER_TOKEN, "") ?: ""
        if (token == "") {
            Toast.makeText(context,
                "Invalid token",
                Toast.LENGTH_LONG).show()
            return
        }
        currentCall = CardService.providerCardApi(token).getCards(currentPage)
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
                Toast.makeText(context,
                    "Failure while requesting cards",
                    Toast.LENGTH_LONG).show()
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
        private const val CLIENT_ID = "d35ff44a403649d9b8834a4b12c67e16"
        private const val CLIENT_SECRET = "a6lyWcR3E8ZWaMJYmwo55XrjaRG41IDW"
        private const val ITEM_TO_LOAD = 20
        private const val GRANT_TYPE = "client_credentials"
        private const val USER_TOKEN = "user_token"
    }

}


package com.rai.hearthstonecard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rai.hearthstonecard.databinding.FragmentListCardBinding
import com.rai.hearthstonecard.retrofit.Card
import com.rai.hearthstonecard.retrofit.CardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListCardFragment : Fragment() {
    private var _binding: FragmentListCardBinding? = null
    private val binding get() = requireNotNull(_binding) {
        "View was destroyed"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListCardBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CardService.providerCardApi().getCards().enqueue(object : Callback<List<Card>>{
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if(response.isSuccessful) {
                    val cards = response.body()
                    println(cards)
                }
            }


            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        with(binding){
           
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
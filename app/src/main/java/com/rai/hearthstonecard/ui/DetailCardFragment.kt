package com.rai.hearthstonecard.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.rai.hearthstonecard.databinding.FragmentDetailCardBinding
import com.rai.hearthstonecard.retrofit.CardItem
import com.rai.hearthstonecard.retrofit.CardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCardFragment : Fragment() {

    private var _binding: FragmentDetailCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<DetailCardFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailCardBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        with(binding) {
            CardService.providerCardApi().getCard(id).enqueue(object : Callback<CardItem.Card> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<CardItem.Card>,
                    response: Response<CardItem.Card>,
                ) {
                    if (response.isSuccessful) {
                        val card = response.body() ?: return
                        imageView.load(card.image)
                        name.text = card.name
                        flavorText.text = card.flavorText
                        textCard.text = card.text.replace("<b>","").replace("</b>","")
                        artistName.text = AUTHOR + card.artistName
                        if (card.collectible == 1) {
                            collectible.text = COLLECTIBLE
                        }
                    } else {
                        Toast.makeText(context,
                            "Failure while requesting cards",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CardItem.Card>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    companion object {
        private const val COLLECTIBLE = "Collectible"
        private const val AUTHOR = "Author: "
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
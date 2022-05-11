package com.rai.hearthstonecard.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.rai.hearthstonecard.HearthstoneApplication
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.databinding.FragmentDetailCardBinding
import com.rai.hearthstonecard.retrofit.Card
import com.rai.hearthstonecard.viewmodels.CardViewModel
import com.rai.hearthstonecard.viewmodels.CardViewModelFactory
import kotlinx.coroutines.launch


class DetailCardFragment : Fragment() {

    private var _binding: FragmentDetailCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel: CardViewModel by activityViewModels {
        CardViewModelFactory(
            (requireActivity().application as HearthstoneApplication).cardRepository
        )
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

            toolbar.setupWithNavController(findNavController())

            viewLifecycleOwner.lifecycleScope.launch {
                val card = viewModel.retrieveCard(id)
                try {
                    val card = viewModel.retrieveCard(id)
                    bind(card)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(),
                        getString(R.string.error_getcards, e.message), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun bind(card: Card){
        with(binding){
            imageView.load(card.image)
            name.text = card.name
            flavorText.text = card.flavorText
            textCard.text = Html.fromHtml(card.text, Html.FROM_HTML_MODE_COMPACT)
            artistName.text = AUTHOR.format(card.artistName)
            if (card.collectible == 1) {
                collectible.text = COLLECTIBLE
            }
        }
    }

    companion object {
        private const val COLLECTIBLE = "Collectible"
        private const val AUTHOR = "Author: %s"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
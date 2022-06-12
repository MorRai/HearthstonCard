package com.rai.hearthstonecard.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.databinding.FragmentDetailCardBinding
import com.rai.hearthstonecard.retrofit.Card
import com.rai.hearthstonecard.util.LceState
import com.rai.hearthstonecard.viewmodels.DetailCardViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailCardFragment : Fragment() {

    private var _binding: FragmentDetailCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<DetailCardViewModel>()

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

            viewModel.cardIdStateFlow.value = id

            lifecycleScope.launch {
                viewModel.cardFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            bind(lce.data)
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

    private fun bind(card: Card) {
        with(binding) {
            imageView.load(card.image)
            name.text = card.name
            flavorText.text = card.flavorText
            textCard.text = Html.fromHtml(card.text, Html.FROM_HTML_MODE_COMPACT)
            artistName.text = requireContext().getString(R.string.author_value).format(card.artistName)
            if (card.collectible == ISCOLLECTIBLE) {
                collectible.text = requireContext().getString(R.string.collectible)
            }
        }
    }

    companion object {
        private const val ISCOLLECTIBLE = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
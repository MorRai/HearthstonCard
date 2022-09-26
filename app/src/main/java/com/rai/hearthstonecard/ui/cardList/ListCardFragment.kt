package com.rai.hearthstonecard.ui.cardList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.adapter.CardAdapter
import com.rai.hearthstonecard.addCardDecoration
import com.rai.hearthstonecard.addPaginationScrollListener
import com.rai.hearthstonecard.appComponent
import com.rai.hearthstonecard.databinding.FragmentListCardBinding
import com.rai.hearthstonecard.domain.model.Constants
import com.rai.hearthstonecard.domain.model.Filters
import com.rai.hearthstonecard.domain.model.LceState
import com.rai.hearthstonecard.ui.cardDetail.DetailCardViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import javax.inject.Inject


class ListCardFragment : Fragment() {

    private var _binding: FragmentListCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<ListCardFragmentArgs>()

    //private val viewModel by viewModel<ListCardViewModel> {
    //    parametersOf(args.classPerson)
  //  }

    private val viewModel: ListCardViewModel by viewModels {
        listCardViewModelFactory.create(args.classPerson)
    }

    @Inject
    lateinit var listCardViewModelFactory: ListCardViewModelFactory.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(Constants.REQUEST_KEY) { _, bundle ->
            viewModel.onFilterChanged(bundle.getSerializable(Constants.EXTRA_KEY) as Filters)
        }
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
                        it.id))
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


            toolbar.menu.findItem(R.id.action_filters)
                .setOnMenuItemClickListener {
                    findNavController().navigate(ListCardFragmentDirections.actionListCardFragmentToFilterCardFragment(viewModel.getFilter()))
                    true
                }

            viewModel.state.onEach { lce ->
                when (lce) {
                    is LceState.Content -> {
                        isVisibleProgressBar(false)
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
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.cardsFlow.onEach {
                adapter.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isVisibleProgressBar(visible:Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }

    companion object {
        private const val SPACE_SIZE = 25
        private const val ITEM_TO_LOAD = 20
    }

}


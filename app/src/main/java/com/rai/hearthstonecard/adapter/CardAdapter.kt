package com.rai.hearthstonecard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rai.hearthstonecard.databinding.ItemCardBinding
import com.rai.hearthstonecard.databinding.ItemLoadingBinding
import com.rai.hearthstonecard.retrofit.CardItem


class CardAdapter(
    context: Context,
    private val onItemClicked: (CardItem.Card) -> Unit,
) : ListAdapter<CardItem, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CardItem.Card -> CARD_TYPE
            CardItem.Loading -> LOADING_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CARD_TYPE -> {
                CardViewHolder(
                    binding = ItemCardBinding.inflate(layoutInflater, parent, false)
                )
            }
            LOADING_TYPE -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Incorrect view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardViewHolder = holder as? CardViewHolder ?: return
        val item = getItem(position) as? CardItem.Card ?: return
        cardViewHolder.itemView.setOnClickListener {
            onItemClicked(item)
        }
        cardViewHolder.bind(item)
    }

    companion object {

        private const val CARD_TYPE = 0
        private const val LOADING_TYPE = 1

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<CardItem>() {
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CardViewHolder(
    private val binding: ItemCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CardItem.Card) {
        binding.imageCard.load(item.image)
    }
}

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
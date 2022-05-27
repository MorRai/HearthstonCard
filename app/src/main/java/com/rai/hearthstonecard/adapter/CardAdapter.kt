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
import com.rai.hearthstonecard.retrofit.Card
import com.rai.hearthstonecard.retrofit.Item


class CardAdapter(
    context: Context,
    private val onItemClicked: (Item.Content<Card>) -> Unit,
) : ListAdapter<Item<*>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    //есть устойчивое чувство что что то не то делаю с Item, но вроде работает
    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Item.Content -> CARD_TYPE
            Item.Loading -> LOADING_TYPE
            else -> error("Incorrect item")
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
        val item = getItem(position) as? Item.Content<Card> ?: return
        cardViewHolder.bind(item) {
            onItemClicked(it)
        }
    }

    companion object {

        private const val CARD_TYPE = 0
        private const val LOADING_TYPE = 1

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Item<*>>() {
            override fun areItemsTheSame(oldItem: Item<*>, newItem: Item<*>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item<*>, newItem: Item<*>): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CardViewHolder(
    private val binding: ItemCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item.Content<Card>, onItemClicked: (Item.Content<Card>) -> Unit) {
        binding.imageCard.load(item.data.image)
        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
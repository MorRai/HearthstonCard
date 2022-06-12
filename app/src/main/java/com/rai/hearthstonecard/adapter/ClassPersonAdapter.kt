package com.rai.hearthstonecard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rai.hearthstonecard.databinding.ItemCardBinding
import com.rai.hearthstonecard.domain.model.ClassPerson


class ClassPersonAdapter(
    context: Context,
    private val onItemClicked: (ClassPerson) -> Unit,
) : ListAdapter<ClassPerson, ClassPersonViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassPersonViewHolder {
        return ClassPersonViewHolder(
            // покуда общий макед для карт и классов потом планирую отделить
            binding = ItemCardBinding.inflate(layoutInflater, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ClassPersonViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item) {
            onItemClicked(it)
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ClassPerson>() {
            override fun areItemsTheSame(
                oldItem: ClassPerson,
                newItem: ClassPerson,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ClassPerson,
                newItem: ClassPerson,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ClassPersonViewHolder(
    private val binding: ItemCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ClassPerson, onItemClicked: (ClassPerson) -> Unit) {
        binding.imageCard.load(item.image)
        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }

}
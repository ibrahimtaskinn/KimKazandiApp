package com.example.kimkazandiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.databinding.RecylerviewItemBinding

class CekilislerAdapter(private val listener: OnItemClickListener? = null) : ListAdapter<Data, CekilislerAdapter.CekilislerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CekilislerViewHolder {
        val binding = RecylerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CekilislerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CekilislerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CekilislerViewHolder(private val binding: RecylerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val data = getItem(position)
                    listener?.onItemClick(data)
                }
            }
        }

        fun bind(data: Data) {
            binding.apply {
                titleTxt.text = data.title
                timeTxt.text = data.time
                giftTxt.text = data.hediye
                conditionTxt.text = data.kosul

                Glide.with(itemView)
                    .load(data.imgUrl)
                    .into(itemImage)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: Data)
    }
}
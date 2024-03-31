package com.md29.husein.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.md29.husein.core.databinding.ItemRowUserBinding
import com.md29.husein.core.domain.model.User

class UserAdapter : ListAdapter<User, UserAdapter.ListViewHolder>(UserDiffCallback()) {

    var onClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemsItem: User) {
            binding.apply {
                tvName.text = itemsItem.name
                idUser.text = itemsItem.id.toString()
                Glide.with(itemView.context).load(itemsItem.avatar).into(imgItemPhoto)
            }
        }

        init {
            binding.root.setOnClickListener {
                onClick?.invoke(getItem(adapterPosition))
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}

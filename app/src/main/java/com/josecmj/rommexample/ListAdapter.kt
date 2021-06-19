package com.josecmj.rommexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josecmj.rommexample.db.ListItem

class ListAdapter(private val onClick: (ListItem) -> Unit,
                  private val onClickDelete: (ListItem) -> Unit)  :
    ListAdapter<ListItem, com.josecmj.rommexample.ListAdapter.ListItemViewHolder>(ItemDiffCallback) {


    class ListItemViewHolder(itemView: View, val onClick: (ListItem) -> Unit, val onClickDelete: (ListItem) -> Unit) :
        RecyclerView.ViewHolder(itemView)  {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.txtItemName)
        private val itemPriceTextView: TextView = itemView.findViewById(R.id.txtItemPrice)
        private val fileImageView: ImageView = itemView.findViewById(R.id.trash_image)
        private var currentItem: ListItem? = null

        fun bind(item: ListItem) {

            currentItem = item
            itemNameTextView.text = item.name
            itemPriceTextView.text = item.price
        }

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
            fileImageView.setOnClickListener{
                currentItem?.let {
                    onClickDelete(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListItemViewHolder(view, onClick, onClickDelete)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.name.equals(newItem.name)
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.name.equals(newItem.name) && oldItem.price.equals(newItem.price)
    }
}
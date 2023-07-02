package com.example.tokoikanhias.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tokoikanhias.R
import com.example.tokoikanhias.model.Ikan

class ikanListAdapter(
    private val onItemClickListener: (Ikan) -> Unit

): ListAdapter<Ikan, ikanListAdapter.ikanViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ikanViewHolder {
        return ikanViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ikanViewHolder, position: Int) {
        val ikan = getItem(position)
        holder.bind(ikan)
        holder.itemView.setOnClickListener {
            onItemClickListener(ikan)
        }
    }

    class ikanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameEditText)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)

        fun bind(ikan: Ikan?) {
            nameTextView.text = ikan?.name
            typeTextView.text = ikan?.type
            addressTextView.text = ikan?.address

        }

        companion object {
            fun create(parent: ViewGroup): ikanListAdapter.ikanViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ikan, parent, false)
                return ikanViewHolder(view)
            }
        }
    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Ikan>(){
            override fun areItemsTheSame(oldItem: Ikan, newItem: Ikan): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Ikan, newItem: Ikan): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
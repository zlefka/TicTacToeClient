package com.example.tictactoemobile.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tic_tac_toe_mobile.R
import com.example.tic_tac_toe_mobile.databinding.ItemGameBinding
import com.example.tictactoemobile.presentation.model.ItemViewData

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private val items = mutableListOf<ItemViewData>()
    class ItemHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = ItemGameBinding.bind(item)
        fun bind(gameItem: ItemViewData) = with(binding) {
            tvGameCreator.text = gameItem.login
            tvGameId.text = gameItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<ItemViewData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
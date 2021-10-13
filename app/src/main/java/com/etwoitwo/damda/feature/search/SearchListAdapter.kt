package com.etwoitwo.damda.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etwoitwo.damda.databinding.ItemSearchListBinding
import com.etwoitwo.damda.model.dataclass.Search
import java.text.DecimalFormat

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    val items = ArrayList<Search>()

    inner class ViewHolder(private val binding: ItemSearchListBinding): RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: Search) {
            val dec = DecimalFormat("#,###")
            val currentPrice = "${dec.format(item.currentPrice)} 원"

            binding.textViewStockMarket.text = item.marketType
            binding.textViewStockCode.text = item.stockId
            binding.textViewStockName.text = item.stockName
            binding.textViewStockCurrentPrice.text = currentPrice
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int = items.size
}
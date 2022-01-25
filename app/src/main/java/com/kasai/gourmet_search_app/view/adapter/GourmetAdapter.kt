package com.kasai.gourmet_search_app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kasai.gourmet_search_app.R
import com.kasai.gourmet_search_app.databinding.GourmetListItemBinding
import com.kasai.gourmet_search_app.service.model.Gourmet
import com.kasai.gourmet_search_app.view.callback.GourmetClickCallback


class GourmetAdapter(private val gourmetClickCallback: GourmetClickCallback?) :
        RecyclerView.Adapter<GourmetAdapter.GourmetViewHolder>() {

    private var gourmetList: Gourmet.Results? = null

    fun setGourmetList(gourmetList: Gourmet.Results) {

        if (this.gourmetList == null) {
            this.gourmetList = gourmetList
            notifyItemRangeInserted(0, gourmetList.shop.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return requireNotNull(this@GourmetAdapter.gourmetList?.shop?.size)
                }

                override fun getNewListSize(): Int {
                    return gourmetList.shop.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldList = this@GourmetAdapter.gourmetList?.shop
                    return oldList?.get(oldItemPosition)?.id == gourmetList.shop[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = gourmetList.shop[newItemPosition]
                    val old = gourmetList.shop[oldItemPosition]
                    return project.id == old.id
                }
            })
            this.gourmetList = gourmetList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): GourmetViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.gourmet_list_item, parent,
                false) as GourmetListItemBinding
        binding.callback = gourmetClickCallback
        return GourmetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GourmetViewHolder, position: Int) {
        holder.binding.gourmet = gourmetList?.shop?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return gourmetList?.shop?.size ?: 0
    }

    open class GourmetViewHolder(val binding: GourmetListItemBinding) : RecyclerView.ViewHolder(binding.root)
}

package com.okation.aivideocreator.view.home_start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.model.FakeYouEntity
import com.okation.aivideocreator.databinding.HomeRowAdapterBinding
import com.okation.aivideocreator.utils.Constants

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: HomeRowAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClick: ((FakeYouEntity) -> Unit)? = null
    private val diffCallback = object : DiffUtil.ItemCallback<FakeYouEntity>() {
        override fun areItemsTheSame(oldItem: FakeYouEntity, newItem: FakeYouEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FakeYouEntity, newItem: FakeYouEntity): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeRowAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            tvUuid.text = currentItem.id.toString()
            tvGeneration.text = Constants.generation
            tvName.text = currentItem.name
            tvUserInputText.text = currentItem.userInputText
            personImage.setImageResource(currentItem.image)

        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(currentItem)
        }
    }
}
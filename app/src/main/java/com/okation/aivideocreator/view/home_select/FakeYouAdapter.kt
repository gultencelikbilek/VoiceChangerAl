package com.okation.aivideocreator.view.home_select

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.VoiceRecyclerviewBinding
import com.okation.aivideocreator.model.VoiceResponse

class FakeYouAdapter(val context: Context, private val pathList: List<VoiceResponse>) :
    RecyclerView.Adapter<FakeYouAdapter.VoiceResponseViewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION
    var onItemClick: ((VoiceResponse) -> Unit)? = null

    class VoiceResponseViewHolder(val binding: VoiceRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceResponseViewHolder {
        return VoiceResponseViewHolder(
            VoiceRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = pathList.size

    override fun onBindViewHolder(holder: VoiceResponseViewHolder, position: Int) {
        val currentItem = pathList[position]
        holder.binding.apply {
            tvTrump.text = currentItem.name
            imgTrump.setImageResource(currentItem.image)


            if (position == selectedPosition) {
                imgTrump.background =
                    ContextCompat.getDrawable(context, R.drawable.image_border_options)
            } else {
                imgTrump.background = ContextCompat.getDrawable(context, R.drawable.image_bacground)
            }

            imgTrump.setOnClickListener {
                val newPosition = holder.adapterPosition
                if (newPosition != RecyclerView.NO_POSITION) {
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = newPosition
                    if (previousSelectedPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previousSelectedPosition)
                    }
                    notifyItemChanged(selectedPosition)
                    onItemClick?.invoke(currentItem)
                }
            }
        }
    }
}
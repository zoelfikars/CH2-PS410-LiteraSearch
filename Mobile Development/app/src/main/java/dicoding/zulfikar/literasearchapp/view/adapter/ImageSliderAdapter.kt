package dicoding.zulfikar.literasearchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dicoding.zulfikar.literasearchapp.databinding.ItemImageSliderBinding
import dicoding.zulfikar.literasearchapp.view.library.ImageData

class ImageSliderAdapter(private val items: List<ImageData>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bind(data: ImageData) {
            with(binding) {
                Glide.with(itemView).load(data.imageUrl).into(imgSlider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
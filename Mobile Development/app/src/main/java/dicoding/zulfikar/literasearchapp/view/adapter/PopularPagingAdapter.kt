package dicoding.zulfikar.literasearchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.local.entity.PopularBookEntity
import dicoding.zulfikar.literasearchapp.databinding.ItemBookBinding

class PopularPagingAdapter(private val onItemClick: (PopularBookEntity) -> Unit, private val isHorizontal: Boolean) :
    PagingDataAdapter<PopularBookEntity, PopularPagingAdapter.MyViewHolder>(PopularPagingAdapter.DIFF_CALLBACK) {
    class MyViewHolder(
        private val binding: ItemBookBinding,
        private val onItemClick: (PopularBookEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PopularBookEntity, isHorizontal: Boolean) {
            with(binding) {
                if(isHorizontal) {
                    val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    cardView.layoutParams = layoutParams
                } else {
                    val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    cardView.layoutParams = layoutParams
                }
                tvItemTitle.text = data.title
                tvItemAuthor.text = data.author
                if (data.title.length > 12) {
                    val title = "${data.title.substring(0, 12)}..."
                    tvItemTitle.text = title
                } else {
                    tvItemTitle.text = data.title
                }
                tvItemAuthor.text = data.author
                root.setOnClickListener { onItemClick(data) }
            }
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, isHorizontal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularBookEntity>() {
            override fun areItemsTheSame(oldItem: PopularBookEntity, newItem: PopularBookEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PopularBookEntity, newItem: PopularBookEntity): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }
}
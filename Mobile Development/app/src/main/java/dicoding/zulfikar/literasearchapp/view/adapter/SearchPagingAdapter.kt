package dicoding.zulfikar.literasearchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.local.entity.SearchedBookEntity
import dicoding.zulfikar.literasearchapp.databinding.ItemBookBinding

class SearchPagingAdapter(private val onItemClick: (SearchedBookEntity) -> Unit) :
    PagingDataAdapter<SearchedBookEntity, SearchPagingAdapter.MyViewHolder>(SearchPagingAdapter.DIFF_CALLBACK) {
    class MyViewHolder(
        private val binding: ItemBookBinding,
        private val onItemClick: (SearchedBookEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchedBookEntity) {
            with(binding) {
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
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchedBookEntity>() {
            override fun areItemsTheSame(oldItem: SearchedBookEntity, newItem: SearchedBookEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchedBookEntity, newItem: SearchedBookEntity): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }
}
package dicoding.zulfikar.literasearchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.databinding.ItemBookBinding

class BookPagingAdapter(private val onItemClick: (BookEntity) -> Unit) :
    PagingDataAdapter<BookEntity, BookPagingAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(
        private val binding: ItemBookBinding,
        private val onItemClick: (BookEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookEntity) {
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookEntity>() {
            override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }

}
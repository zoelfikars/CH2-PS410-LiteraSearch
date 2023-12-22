package dicoding.zulfikar.literasearchapp.view.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.local.entity.BookEntity
import dicoding.zulfikar.literasearchapp.databinding.ItemBookBinding

class BookPagingAdapter(private val onItemClick: (BookEntity) -> Unit) :
    PagingDataAdapter<BookEntity, BookPagingAdapter.BookViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onItemClick)

    }

    class BookViewHolder(
        private val binding: ItemBookBinding, private val onItemClick: (BookEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookEntity) {
            binding.root.setOnClickListener {
                onItemClick(data)
            }
//            Glide.with(binding.root.context).load(data.coverUrl).into(binding.imgItemPhoto)
            binding.tvItemAuthor.text = data.author
            binding.tvItemTitle.text = data.title
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookEntity>() {
            override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: BookEntity,
                newItem: BookEntity
            ): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }

}
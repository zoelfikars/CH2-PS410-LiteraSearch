package dicoding.zulfikar.literasearchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.local.entity.LibraryBookEntity
import dicoding.zulfikar.literasearchapp.databinding.ItemBookBinding

class LibraryBookPagingAdapter(private val onItemClick: (LibraryBookEntity) -> Unit) :
    PagingDataAdapter<LibraryBookEntity, LibraryBookPagingAdapter.MyViewHolder>(LibraryBookPagingAdapter.DIFF_CALLBACK) {
    class MyViewHolder(
        private val binding: ItemBookBinding,
        private val onItemClick: (LibraryBookEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LibraryBookEntity) {
            with(binding) {
                val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                cardView.layoutParams = layoutParams
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LibraryBookEntity>() {
            override fun areItemsTheSame(oldItem: LibraryBookEntity, newItem: LibraryBookEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LibraryBookEntity, newItem: LibraryBookEntity): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }
}
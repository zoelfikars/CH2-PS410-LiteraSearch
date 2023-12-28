package dicoding.zulfikar.literasearchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.model.Book
import dicoding.zulfikar.literasearchapp.databinding.ItemBookBinding

class BookAdapter(private val onItemClick: (Book) -> Unit, private val isHorizontal: Boolean) :
    ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val library = getItem(position)
        holder.bind(library, position == itemCount-1, isHorizontal)
    }

    class BookViewHolder(
        private val binding: ItemBookBinding,
        private val onItemClick: (Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book, lastItem: Boolean, isHorizontal: Boolean) {
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
                if (book.title.length > 12) {
                    val title = "${book.title.substring(0, 12)}..."
                    tvItemTitle.text = title
                } else {
                    tvItemTitle.text = book.title
                }
                tvItemAuthor.text = book.author
                root.setOnClickListener { onItemClick(book) }
            }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.idPerpus == newItem.idPerpus
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}
package dicoding.zulfikar.literasearchapp.view.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dicoding.zulfikar.literasearchapp.data.model.Library
import dicoding.zulfikar.literasearchapp.databinding.ItemLibraryBinding

class LibraryAdapter(private val onItemClick: (Library) -> Unit) :
    ListAdapter<Library, LibraryAdapter.LibraryViewHolder>(LibraryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLibraryBinding.inflate(inflater, parent, false)
        return LibraryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val library = getItem(position)
        holder.bind(library)
    }

    class LibraryViewHolder(
        private val binding: ItemLibraryBinding,
        private val onItemClick: (Library) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(library: Library) {
            binding.tvNamaPerpus.text = library.perpustakaan
            binding.tvJarak.text = library.alamat
            binding.root.setOnClickListener { onItemClick(library) }
        }
    }
}
class LibraryDiffCallback : DiffUtil.ItemCallback<Library>() {
    override fun areItemsTheSame(oldItem: Library, newItem: Library): Boolean {
        return oldItem.idPerpus == newItem.idPerpus
    }

    override fun areContentsTheSame(oldItem: Library, newItem: Library): Boolean {
        return oldItem == newItem
    }
}


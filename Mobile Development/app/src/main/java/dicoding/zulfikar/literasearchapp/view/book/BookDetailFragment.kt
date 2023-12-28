package dicoding.zulfikar.literasearchapp.view.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.data.model.Book
import dicoding.zulfikar.literasearchapp.data.model.library
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.databinding.FragmentBookDetailBinding
import dicoding.zulfikar.literasearchapp.utility.dpToPx
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.adapter.LibraryAdapter

class BookDetailFragment : Fragment() {
    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var adapter = LibraryAdapter { item ->
        move(BookDetailFragmentDirections
            .actionBookDetailFragmentToLibraryDetailFragment(item))
    }

    private var bookData: Book? = null

    private var showLibrary = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG = "BookDetailFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupView() {
        setupBookData()
    }

    private fun setupAction() {
        with(binding) {
            val layoutParams = cvDropdown.layoutParams as ConstraintLayout.LayoutParams

            imageButton4.setOnClickListener {
                move(BookDetailFragmentDirections.actionBookDetailFragmentToMainFragment("bookdetail"))
            }
            imageButton5.setOnClickListener {
                if (showLibrary) {
                    showLibrary = false
//                    val layoutParams = cvDropdown.layoutParams as ViewGroup.MarginLayoutParams
//                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    imageButton5.setImageResource(R.drawable.ic_arrow_up)
                    rvPerpustakaanDetail.visibility = View.GONE
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    cvDropdown.layoutParams = layoutParams
                } else {
                    showLibrary = true
//                    val layoutParams = cvDropdown.layoutParams as ViewGroup.MarginLayoutParams
//                    layoutParams.width = dpToPx(500)
                    imageButton5.setImageResource(R.drawable.ic_arrow_down)
                    rvPerpustakaanDetail.visibility = View.VISIBLE
                    layoutParams.height = dpToPx(500) // Gantilah dengan metode konversi dp ke px yang sesuai dengan kebutuhan Anda
                    cvDropdown.layoutParams = layoutParams
                }
            }
        }
    }

    private fun setupBookData() {
        val receivedData = BookDetailFragmentArgs.fromBundle(requireArguments()).bookData
        bookData = receivedData
        if (receivedData != null) {
            with(binding) {
                tvTitle.text = receivedData.title
                tvAuthor.text = receivedData.author
                tvRating.text =
                    "${(0..10).shuffled().last()}/10"//cc should add more data books (rating)
                val desc = if (receivedData.description?.get(0).isNullOrEmpty()) {
                    "belum ada deskripsi buku"
                } else {
                    receivedData.description?.get(0)
                }

                tvSinopsis.text = desc
//                Glide.with(requireContext().load(receivedData.image).into(imgPerpus)
                val list = listOf(receivedData.idPerpus)
//                val list = listOf(913,213,173)
                     // we'll change to list later, after we get the library id in list on every single book
                setupLibrary(list)
            }
        }
    }

    private fun setupLibrary(id: List<Int>) {
//        viewModel.getLibraryWithId(id).observe(viewLifecycleOwner) { result ->
//            when (result) {
//                is Result.Loading -> {
//                    showLoading(true)
//                }
//
//                is Result.Success -> {
//                    adapter.submitList(result.data)
//                    setupRecyclerView()
//                    showLoading(false)
//                }
//
//                is Result.Error -> {
//                    showLoading(false)
//                }
//            }

        viewModel.getLibrary().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    val filteredList = library.filter { library ->
                        id.contains(library.idPerpus)
                    }
                    adapter.submitList(filteredList)
//                    adapter.submitList(result.data)
                    setupRecyclerView()
                    showLoading(false)
                }

                is Result.Error -> {
                    showLoading(false)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        with(binding) {
            rvPerpustakaanDetail.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPerpustakaanDetail.adapter = adapter
        }
    }

    private fun showLoading(condition: Boolean) {
        with(binding) {
            progressBar5.visibility = if (condition) View.VISIBLE else View.GONE
        }
    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }
}

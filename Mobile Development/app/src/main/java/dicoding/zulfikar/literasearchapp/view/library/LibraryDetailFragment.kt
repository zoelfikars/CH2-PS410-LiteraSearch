package dicoding.zulfikar.literasearchapp.view.library

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.data.model.Book
import dicoding.zulfikar.literasearchapp.data.model.Library
import dicoding.zulfikar.literasearchapp.databinding.FragmentLibraryDetailBinding
import dicoding.zulfikar.literasearchapp.utility.AdaptiveSpacingItemDecoration
import dicoding.zulfikar.literasearchapp.utility.dpToPx
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.adapter.BookAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.LibraryBookPagingAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.LoadingStateAdapter

class LibraryDetailFragment : Fragment() {
    private var _binding: FragmentLibraryDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var adapter = BookAdapter({ item ->
        val copy = item.copy()
        move(
            LibraryDetailFragmentDirections
                .actionLibraryDetailFragmentToBookDetailFragment3(copy)
        )
    }, isHorizontal = true)
    private var libraryData: Library? = null
    private var showBook = true

    companion object {
        val TAG = "LibraryDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupView() {
        setUpLibraryData()
    }

    private fun setupAction() {
        with(binding) {
            imageButton4.setOnClickListener {
                move(LibraryDetailFragmentDirections.actionLibraryDetailFragmentToMainFragment(
                    "librarydetail"
                ))
            }
            imageButton3.setOnClickListener {
                if (showBook) {
                    showBook = false
                    imageButton3.setImageResource(R.drawable.ic_arrow_up)
                    recyclerView.visibility = View.GONE
                } else {
                    showBook = true
                    imageButton3.setImageResource(R.drawable.ic_arrow_down)
                    recyclerView.visibility = View.VISIBLE
                }
            }
            floatingActionButton.setOnClickListener {
                if (libraryData != null) {
                    val uri =
                        Uri.parse("geo:0,0?q=${libraryData!!.latitude},${libraryData!!.longitude}(label)")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    startActivity(intent)
                }
            }
        }
    }

    private fun setUpLibraryData() {
        val receivedData = LibraryDetailFragmentArgs.fromBundle(requireArguments()).libraryData
        libraryData = receivedData
        if (receivedData != null) {
            with(binding) {
                tvTitle.text = receivedData.perpustakaan
                tvAlamat.text = receivedData.alamat
//                Glide.with(requireContext().load(receivedData.image).into(imgPerpus)
//                setupBook(receivedData.idPerpus.toString())
                setupPagingBook(receivedData.idPerpus.toString())
            }
        }

    }

    private fun setupPagingBook(id: String) {
        var adapter = LibraryBookPagingAdapter { item ->
            val book = Book(
                title = item.title,
                author = item.author,
                idPerpus = item.idPerpus,
                coverUrl = item.coverUrl,
                description = listOf(item.description),
                isbn = item.isbn,
                publicationYear = item.publicationYear,
                publisher = item.publisher,
                subject = listOf(item.subject),
            )
            move(LibraryDetailFragmentDirections.actionLibraryDetailFragmentToBookDetailFragment3(book))
        }
        with(binding) {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.addItemDecoration(AdaptiveSpacingItemDecoration(dpToPx(10), false))
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.getPagingLibraryBook(id).observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }
}

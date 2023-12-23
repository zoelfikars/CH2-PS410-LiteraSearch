package dicoding.zulfikar.literasearchapp.view.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.zulfikar.literasearchapp.databinding.FragmentBookBinding
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.paging.LoadingStateAdapter

class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launch {
//            var trendingAdapter = BookPagingAdapter { selectedStory ->
//                val items = BookModel(
//                    coverUrl = selectedStory.coverUrl,
//                    publicationYear = selectedStory.publicationYear,
//                    author = selectedStory.author,
//                    subject = selectedStory.subject,
//                    isbn = selectedStory.isbn,
//                    publisher = selectedStory.publisher,
//                    description = selectedStory.description,
//                    title = selectedStory.title,
//                    idPerpus = selectedStory.idPerpus
//                )
//                val action = BookFragmentDirections.actionBookFragmentToBookDetailFragment()
//                findNavController().navigate(action)
//            }
//            setupPaging(trendingAdapter)
//        }
        setupAction()
    }

    private fun setupAction() {
        with(binding) {
            imageView9.setOnClickListener {
                val action = BookFragmentDirections.actionBookFragmentToBookDetailFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setupPaging(trendingAdapter: BookPagingAdapter) {
        val adapter = trendingAdapter
        binding.rvTrending.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrending.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getTrendingBook().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
            Log.d("IJUL", "di setup paging ${adapter.itemCount}")
        }
    }

}
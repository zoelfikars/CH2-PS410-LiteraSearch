package dicoding.zulfikar.literasearchapp.view.book

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.data.model.Book
import dicoding.zulfikar.literasearchapp.databinding.FragmentBookBinding
import dicoding.zulfikar.literasearchapp.utility.AdaptiveSpacingItemDecoration
import dicoding.zulfikar.literasearchapp.utility.dpToPx
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.adapter.BookPagingAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.ImageSliderAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.LoadingStateAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.PopularPagingAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.SearchPagingAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.TrendingPagingAdapter
import dicoding.zulfikar.literasearchapp.view.library.ImageData
import dicoding.zulfikar.literasearchapp.view.menu.MainFragmentDirections


class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var isIdle: Boolean = true
    private val itemDecoration = AdaptiveSpacingItemDecoration(dpToPx(10), false)
    private lateinit var sliderAdapter: ImageSliderAdapter
    private lateinit var dots: ArrayList<TextView>
    private var dummyList = ArrayList<ImageData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupView() {
        setupSlider()
        setupPagingPopularBookHorizontal()
        setupPagingTrendingBookHorizontal()
        setupPagingOtherBook()
    }

    private fun setupSlider() {
        addDummyList()
        setupSliderAdapter()
    }

    private fun setupSliderAdapter() {
        sliderAdapter = ImageSliderAdapter(dummyList)
        binding.vpSliderBook.adapter = sliderAdapter
        dots = ArrayList()
        setIndicator()
        binding.vpSliderBook.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pos: Int) {
                selectedDot(pos)
                super.onPageSelected(pos)
            }
        })

    }

    private fun selectedDot(position: Int) {
        for (i in 0 until dummyList.size) {
            if (i == position) {
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            } else {
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }
    }

    private fun setIndicator() {
        for (i in 0 until dummyList.size) {
            dots.add(TextView(requireContext()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 12f
            binding.dotsIndicator.addView(dots[i])
        }
    }

    private fun addDummyList() {
        dummyList.add(ImageData("https://covers.openlibrary.org/b/id/6108952-M.jpg"))
        dummyList.add(ImageData("https://covers.openlibrary.org/w/id/6738557-M.jpgjpg"))
        dummyList.add(ImageData("https://covers.openlibrary.org/w/id/4763928-M.jpg"))
        dummyList.add(ImageData("https://ia800505.us.archive.org/view_archive.php?archive=/25/items/m_covers_0010/m_covers_0010_55.zip&file=0010556049-M.jpg"))
    }

    private fun setupAction() {
        with(binding) {
            bookBack.setOnClickListener {
                vpSliderBook.visibility = View.VISIBLE
                textView14.visibility = View.VISIBLE
                textView16.visibility = View.VISIBLE
                textView11.visibility = View.VISIBLE
                trendingShowMore.visibility = View.VISIBLE
                rvTrending.visibility = View.VISIBLE
                rvListBook.visibility = View.VISIBLE
                populerShowMore.visibility = View.VISIBLE
                rvPopuler.visibility = View.VISIBLE
                bookBack.visibility = View.GONE
                textView22.visibility = View.GONE
                setupView()
                isIdle = true
            }
            populerShowMore.setOnClickListener {
                vpSliderBook.visibility = View.GONE
                textView14.visibility = View.GONE
                textView16.visibility = View.GONE
                populerShowMore.visibility = View.GONE
                trendingShowMore.visibility = View.GONE
                rvTrending.visibility = View.GONE
                rvListBook.visibility = View.GONE
                bookBack.visibility = View.VISIBLE
                isIdle = false
                setupPagingPopulerBook()
            }
            trendingShowMore.setOnClickListener {
                vpSliderBook.visibility = View.GONE
                textView11.visibility = View.GONE
                textView16.visibility = View.GONE
                trendingShowMore.visibility = View.GONE
                populerShowMore.visibility = View.GONE
                rvPopuler.visibility = View.GONE
                rvListBook.visibility = View.GONE
                bookBack.visibility = View.VISIBLE
                isIdle = false
                setupPagingTrendingBook()
            }
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    vpSliderBook.visibility = View.GONE
                    textView14.visibility = View.GONE
                    textView16.visibility = View.GONE
                    textView11.visibility = View.GONE
                    textView22.visibility = View.VISIBLE
                    trendingShowMore.visibility = View.GONE
                    rvTrending.visibility = View.GONE
                    rvPopuler.visibility = View.GONE
                    populerShowMore.visibility = View.GONE
                    bookBack.visibility = View.VISIBLE
                    val searchText = s.toString()
                    val template = "Menampilkan buku dengan kata kunci '$searchText'"
                    textView22.text = template
                    searchBook(searchText)
                }
            })

        }
    }

    private fun searchBook(key: String) {
        var adapter = SearchPagingAdapter { item ->
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
            move(MainFragmentDirections.actionMainFragmentToBookDetailFragment(book))
        }
        with(binding) {
            rvListBook.removeItemDecoration(itemDecoration)
            val spanCount = 3
            val layoutManager = GridLayoutManager(context, spanCount)
            rvListBook.layoutManager = layoutManager
            rvListBook.addItemDecoration(itemDecoration)
            rvListBook.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.searchBook(key).observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupPagingOtherBook() {
        var adapter = BookPagingAdapter { item ->
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
            move(MainFragmentDirections.actionMainFragmentToBookDetailFragment(book))
        }
        with(binding) {
            rvListBook.removeItemDecoration(itemDecoration)
            val spanCount = 3
            val layoutManager = GridLayoutManager(context, spanCount)
            rvListBook.layoutManager = layoutManager
            rvListBook.addItemDecoration(itemDecoration)
            rvListBook.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.book.observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupPagingPopulerBook() {
        var adapter = PopularPagingAdapter({ item ->
            val book = Book(
                title = item.title,
                author = item.author,
                idPerpus = item.idPerpus,
                coverUrl = item.coverUrl,
                description = listOf(item.description!!),
                isbn = item.isbn,
                publicationYear = item.publicationYear,
                publisher = item.publisher,
                subject = listOf(item.subject!!),
            )
            move(MainFragmentDirections.actionMainFragmentToBookDetailFragment(book))
        }, isHorizontal = false)
        with(binding) {
            rvPopuler.removeItemDecoration(itemDecoration)
            val spanCount = 3
            val layoutManager = GridLayoutManager(context, spanCount)
            rvPopuler.layoutManager = layoutManager
            rvPopuler.addItemDecoration(itemDecoration)
            rvPopuler.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.popular.observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupPagingPopularBookHorizontal() {
        var adapter = PopularPagingAdapter({ item ->
            val book = Book(
                title = item.title,
                author = item.author,
                idPerpus = item.idPerpus,
                coverUrl = item.coverUrl,
                description = listOf(item.description!!),
                isbn = item.isbn,
                publicationYear = item.publicationYear,
                publisher = item.publisher,
                subject = listOf(item.subject!!),
            )
            move(MainFragmentDirections.actionMainFragmentToBookDetailFragment(book))
        }, isHorizontal = true)
        with(binding) {
            rvPopuler.removeItemDecoration(itemDecoration)
            rvPopuler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvPopuler.addItemDecoration(itemDecoration)
            rvPopuler.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.popular.observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupPagingTrendingBook() {
        var adapter = TrendingPagingAdapter({ item ->
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
            move(MainFragmentDirections.actionMainFragmentToBookDetailFragment(book))
        }, isHorizontal = false)
        with(binding) {
            rvTrending.removeItemDecoration(itemDecoration)
            val spanCount = 3
            val layoutManager = GridLayoutManager(context, spanCount)
            rvTrending.layoutManager = layoutManager
            rvTrending.addItemDecoration(itemDecoration)
            rvTrending.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.trending.observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupPagingTrendingBookHorizontal() {
        var adapter = TrendingPagingAdapter({ item ->
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
            move(MainFragmentDirections.actionMainFragmentToBookDetailFragment(book))
        }, isHorizontal = true)
        with(binding) {
            rvTrending.removeItemDecoration(itemDecoration)
            rvTrending.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTrending.addItemDecoration(itemDecoration)
            rvTrending.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.trending.observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showLoading(condition: Boolean, pb: ProgressBar) {
        pb.visibility = if (condition) View.VISIBLE else View.GONE
    }

    private fun showReload(condition: Boolean, pb: ImageButton) {
        pb.visibility = if (condition) View.VISIBLE else View.GONE
    }
}
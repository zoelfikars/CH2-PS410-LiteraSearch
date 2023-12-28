package dicoding.zulfikar.literasearchapp.view.library

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.databinding.FragmentLibraryBinding
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.adapter.ImageSliderAdapter
import dicoding.zulfikar.literasearchapp.view.adapter.LibraryAdapter
import dicoding.zulfikar.literasearchapp.view.menu.MainFragmentDirections

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var adapter = LibraryAdapter { item ->
        move(MainFragmentDirections.actionMainFragmentToLibraryDetailFragment(item))
    }
    private lateinit var sliderAdapter: ImageSliderAdapter
    private lateinit var dots: ArrayList<TextView>
    private var dummyList = ArrayList<ImageData>()

    companion object {
        val TAG = "LibraryFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupAction() {
        with(binding) {
            libraryBack.setOnClickListener {
                viewPager.visibility = View.VISIBLE
                libraryBack.visibility = View.GONE
                textView6.visibility = View.GONE
            }
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val searchText = s.toString()
                    val template = "Menampilkan perpustakaan dengan kata kunci '$searchText'"
                    if(!searchText.isEmpty()) {
                        viewPager.visibility = View.GONE
                        libraryBack.visibility = View.VISIBLE
                        textView6.visibility = View.VISIBLE
                        textView6.text = template
                        adapter.filter(searchText)
                        setupRecyclerView()
                    } else {
                        viewPager.visibility = View.VISIBLE
                        libraryBack.visibility = View.GONE
                        textView6.visibility = View.GONE
                    }
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupView() {
        setupLibrary()
        setupSlider()
    }

    private fun setupSlider() {
        addDummyList()
        setupSliderAdapter()
    }

    private fun setupSliderAdapter() {
        sliderAdapter = ImageSliderAdapter(dummyList)
        binding.viewPager.adapter = sliderAdapter
        dots = ArrayList()
        setIndicator()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
        dummyList.add(ImageData("https://1.bp.blogspot.com/-PAUfzu01joo/XmCqxXJb4MI/AAAAAAAABx0/9ZENWp_5xTUiLWr3j4fFwPWaBcfijuMuQCLcBGAsYHQ/s1600/%255BJejakakhi%255DPersyaratan-dan-Cara-Pendaftaran-Anggota-Dinas-Perpustakaan-dan-Kearsipan-Kota-Bandung1.jpg"))
        dummyList.add(ImageData("https://cdn.idntimes.com/content-images/community/2020/07/dispusipda-88e1f19877b131e1c9a4b1a132276bc4.jpg"))
        dummyList.add(ImageData("https://asset-2.tstatic.net/jabar/foto/bank/images/alun-alun_20181001_104856.jpg"))
        dummyList.add(ImageData("https://asset-2.tstatic.net/jabar/foto/bank/images/tampak-depan-perpus_20170626_102724.jpg"))
    }

    private fun setupLibrary() {
        viewModel.getLibrary().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    adapter.setData(result.data)
                    adapter.notifyDataSetChanged()
                    setupRecyclerView()
                    showLoading(false)
                }

                is Result.Error -> {
                    showLoading(false)
                    setupLibrary()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvLibrary.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvLibrary.setHasFixedSize(true)
            rvLibrary.adapter = adapter
        }
    }

    private fun showLoading(condition: Boolean) {
        with(binding) {
            progressBar3.visibility = if (condition) View.VISIBLE else View.GONE
        }
    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }
}

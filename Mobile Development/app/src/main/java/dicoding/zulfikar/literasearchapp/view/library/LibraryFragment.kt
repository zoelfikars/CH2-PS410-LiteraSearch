package dicoding.zulfikar.literasearchapp.view.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.zulfikar.literasearchapp.data.remote.Result
import dicoding.zulfikar.literasearchapp.databinding.FragmentLibraryBinding
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.menu.MainFragmentDirections
import kotlinx.coroutines.launch

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private var adapter = LibraryAdapter { item ->
        move(MainFragmentDirections.actionMainFragmentToLibraryDetailFragment())
    }

    companion object {
        val TAG = "LibraryFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
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
    }

    private fun setupLibrary() {
        getDataLibrary()
    }

    private fun getDataLibrary() {
        lifecycleScope.launch {
            val result = viewModel.getLibrary()
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    adapter.submitList(result.data)
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
            rvLibrary.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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

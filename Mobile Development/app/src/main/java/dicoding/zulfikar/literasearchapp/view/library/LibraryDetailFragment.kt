package dicoding.zulfikar.literasearchapp.view.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dicoding.zulfikar.literasearchapp.databinding.FragmentLibraryDetailBinding

class LibraryDetailFragment : Fragment() {
    private var _binding: FragmentLibraryDetailBinding? = null
    private val binding get() = _binding!!
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
    }

    private fun setupView() {
        setUpLibraryData()
    }

    private fun setUpLibraryData() {
        val receivedData = LibraryDetailFragmentArgs.fromBundle(requireArguments()).libraryData
        if (receivedData != null) {
            with(binding) {

            }
        }

    }
}
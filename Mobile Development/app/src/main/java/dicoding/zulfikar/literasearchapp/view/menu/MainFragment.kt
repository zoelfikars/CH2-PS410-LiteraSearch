package dicoding.zulfikar.literasearchapp.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.databinding.FragmentMainBinding
import dicoding.zulfikar.literasearchapp.view.MainViewModel
import dicoding.zulfikar.literasearchapp.view.ViewModelFactory
import dicoding.zulfikar.literasearchapp.view.book.BookFragment
import dicoding.zulfikar.literasearchapp.view.library.LibraryFragment
import dicoding.zulfikar.literasearchapp.view.profile.ProfileFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        with(binding) {
            replaceFragment(LibraryFragment())
            bottomnav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.library -> {
                        replaceFragment(LibraryFragment())
                        true
                    }

                    R.id.book -> {
                        replaceFragment(BookFragment())
                        true

                    }

                    R.id.profile -> {
                        replaceFragment(ProfileFragment())
                        true
                    }

                    else -> {
                        super.onOptionsItemSelected(menuItem)
                        false
                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
    }
}
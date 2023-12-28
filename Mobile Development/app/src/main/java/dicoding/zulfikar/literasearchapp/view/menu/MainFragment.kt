package dicoding.zulfikar.literasearchapp.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.databinding.FragmentMainBinding
import dicoding.zulfikar.literasearchapp.view.book.BookFragment
import dicoding.zulfikar.literasearchapp.view.library.LibraryFragment
import dicoding.zulfikar.literasearchapp.view.profile.ProfileFragment

class MainFragment : Fragment() {
    private var menuReceived: String? = null
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
        setupView()
        setupAction()
    }

    private fun setupView() {
        val receivedData = MainFragmentArgs.fromBundle(requireArguments()).menuDirections
        menuReceived = receivedData
        with(binding) {
            if (receivedData.isNotEmpty()) {
                when (receivedData) {
                    "librarydetail" -> {
                        bottomnav.selectedItemId = R.id.library
                        replaceFragment(LibraryFragment())
                    }

                    "bookdetail" -> {
                        bottomnav.selectedItemId = R.id.book
                        replaceFragment(BookFragment())
                    }
                    "welcome" -> {
                        bottomnav.selectedItemId = R.id.book
                        replaceFragment(BookFragment())
                    }
                    "login" -> {
                        bottomnav.selectedItemId = R.id.book
                        replaceFragment(BookFragment())
                    }
                    "register" -> {
                        bottomnav.selectedItemId = R.id.book
                        replaceFragment(BookFragment())
                    }
                }
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.library -> {
                return true
            }

            R.id.book -> {
                item.isChecked = true // Menandai item buku sebagai yang terpilih
                return true
            }

            R.id.profile -> {
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun setupAction() {
        with(binding) {
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
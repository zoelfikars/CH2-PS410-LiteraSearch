package dicoding.zulfikar.literasearchapp.view.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.databinding.FragmentProfileBinding
import dicoding.zulfikar.literasearchapp.utility.isValidEmail
import dicoding.zulfikar.literasearchapp.utility.showToast
import dicoding.zulfikar.literasearchapp.view.menu.MainFragmentDirections

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    companion object {
        val TAG = "ProfileFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setupView()
        setupAction()
    }

    private fun setupView() {
        val currentUser = auth.currentUser
        with(binding) {
            if (currentUser != null) {
                val userDocumentRef = db.collection("user").document(currentUser.uid)
                userDocumentRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        with(binding) {
                            if (documentSnapshot.exists()) {
                                button3.visibility = View.VISIBLE
                                btLogout.visibility = View.VISIBLE
                                textView12.visibility = View.VISIBLE
                                textInputLayout.visibility = View.VISIBLE
                                tvName.text = documentSnapshot.getString("name")
                                tvEmail.text = documentSnapshot.getString("email")
                            } else {

                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                    }
            } else {
                button3.visibility = View.GONE
                btLogout.visibility = View.GONE
                textView12.visibility = View.GONE
                textInputLayout.visibility = View.GONE
                tvName.text = "anonim"
                tvEmail.text = "anonim@gmail.com"
            }
        }

    }

    private fun setupAction() {
        with(binding) {
            btLogout.setOnClickListener {
                auth.signOut()
                move(MainFragmentDirections.actionMainFragmentToLoginFragment())
            }
            button3.setOnClickListener {
                val email = etNewEmail.text.toString()
                if(auth.currentUser != null) {
//                    butuh firebase upgrade
                    Log.d(TAG, "masuk if ada user auth")
                    if(isValidEmail(email)) {
                        showToast("Silakan cek email anda untuk verifikasi", requireContext())
                        Log.d(TAG, "masuk if pas button ganti di klik")
                        requestNewEmail(email)
                    } else {
                        Log.d(TAG, "masuk else pas button ganti di klik")
                        showToast("Masukkan email dengan benar", requireContext())
                    }
                } else {
                    showToast("Anda login sebagai anonim", requireContext())
                    Log.d(TAG, "masuk else gaada user auth")
                }

            }
        }
    }

    private fun requestNewEmail(email: String) {
        val user = auth.currentUser
        user?.updateEmail(email)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Email berhasil diperbarui", requireContext())
                    Log.d(TAG, "Email berhasil diperbarui")
                } else {
                    showToast("Gagal memperbarui email", requireContext())
                    Log.w(TAG, "Gagal memperbarui email", task.exception)
                }
            }

    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }
}
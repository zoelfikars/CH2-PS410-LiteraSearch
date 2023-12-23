package dicoding.zulfikar.literasearchapp.view.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dicoding.zulfikar.literasearchapp.databinding.FragmentRegisterBinding
import dicoding.zulfikar.literasearchapp.utility.showToast
import dicoding.zulfikar.literasearchapp.view.login.LoginFragmentDirections

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    companion object {
        val TAG = "RegisterFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setupAction()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            move(LoginFragmentDirections.actionLoginFragmentToMainFragment())
        }
    }

    private fun setupAction() {
        with(binding) {
            daftarButton.setOnClickListener { register() }
            googleRegister.setOnClickListener { registerGoogle() }
            tvLogin.setOnClickListener { move(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()) }
        }
    }

    private fun register() {
        with(binding) {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            showLoading(true)
            if (password.length >= 8) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) {
                        if (it.isSuccessful) {
                            insertUserIdentity(name, email)
                            showLoading(false)
                            showToast(
                                "Berhasil membuat akun, dan mengarahkan ke menu utama",
                                requireContext()
                            )
                            move(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                            Log.d(TAG, "createUserWithEmail:success")
                        } else {
                            showLoading(false)
                            val message = it.exception?.message
                            val errorCode = (it.exception as? FirebaseAuthException)?.errorCode
                            if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                                showToast(
                                    "Email sudah terdaftar. Gunakan email lain.",
                                    requireContext()
                                )
                            } else {
                                showToast("Gagal membuat akun: $message", requireContext())
                            }
                            Log.d(
                                TAG,
                                "exception : ${it.exception} \n message : $message errorCode : $errorCode"
                            )
                        }
                    }
            }
        }
    }

    private fun insertUserIdentity(name: String, email: String) {
        val data = hashMapOf(
            "name" to name,
            "email" to email,
        )
        val collectionReference = db.collection("user")
        collectionReference.add(data).addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Dokumen berhasil ditambahkan dengan ID: ${documentReference.id}")
        }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error menambahkan dokumen", e)
            }
    }

    private fun registerGoogle() {
    }

    private fun showLoading(condition: Boolean) {
        with(binding) {
            progressBar2.visibility = if (condition) View.VISIBLE else View.GONE
        }
    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }
}
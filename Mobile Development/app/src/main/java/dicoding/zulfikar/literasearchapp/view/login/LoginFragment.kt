package dicoding.zulfikar.literasearchapp.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dicoding.zulfikar.literasearchapp.R
import dicoding.zulfikar.literasearchapp.databinding.FragmentLoginBinding
import dicoding.zulfikar.literasearchapp.utility.isValidEmail
import dicoding.zulfikar.literasearchapp.utility.showToast

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val REQ_ONE_TAP = 2
    private lateinit var googleSignInClient: GoogleSignInClient


    companion object {
        val TAG = "LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setupGoogleSign()
        setupAction()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            move(LoginFragmentDirections.actionLoginFragmentToMainFragment("login"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_ONE_TAP -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                    showToast("Gagal masuk menggunakan google", requireActivity())
                }

            }
        }
    }

    private fun setupAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
                move(action)
            }
        }

        requireActivity().onBackPressedDispatcher
        with(binding) {
            buttonMasuk.setOnClickListener {
                login()
            }
            tvDaftar.setOnClickListener {
                move(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            tvLupaSandi.setOnClickListener {
                move(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
            }
            buttonGoogle.setOnClickListener {
                loginGoogle()
            }
        }
    }

    private fun login() {
        with(binding) {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if(isValidEmail(email) && password.length >= 8) {
                showLoading(true)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            showLoading(false)
                            Log.d(TAG, "signInWithEmail:success")
                            showToast("Berhasil masuk", requireContext())
                            move(LoginFragmentDirections.actionLoginFragmentToMainFragment("login"))
                        } else {
                            showLoading(false)
                            when (task.exception) {
                                is FirebaseAuthInvalidUserException -> {
                                    showToast("Email belum terdaftar", requireContext())
                                }

                                is FirebaseAuthInvalidCredentialsException -> {
                                    showToast("Email atau password salah", requireContext())
                                }

                                else -> {
                                    showToast("Gagal masuk, silahkan coba lagi", requireContext())
                                }
                            }
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                        }
                    }
            } else {
                showToast("Tolong lengkapi email dan password", requireContext())
            }
        }
    }

    private fun showLoading(condition: Boolean) {
        with(binding) {
            progressBar.visibility = if (condition) View.VISIBLE else View.GONE
            tvDaftar
        }
    }

    private fun setupGoogleSign() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun loginGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_ONE_TAP)
    }

    private fun firebaseAuthWithGoogle(token: String?) {
        val credential = GoogleAuthProvider.getCredential(token, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    showToast("Autentikasi berhasil", requireContext())
                    move(LoginFragmentDirections.actionLoginFragmentToMainFragment("login"))
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    showToast("Autentikasi gagal", requireContext())
                }
            }
    }

    private fun move(mover: NavDirections) {
        findNavController().navigate(mover)
    }
}

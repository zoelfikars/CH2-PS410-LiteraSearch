package dicoding.zulfikar.literasearchapp.utility

import android.content.Context
import android.widget.Toast
import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )
    return emailPattern.matcher(email).matches()
}


fun showToast(message: String, context: Context) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT,
    ).show()
}
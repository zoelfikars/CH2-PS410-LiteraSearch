package dicoding.zulfikar.literasearchapp.view.custom

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class NamaEditText : AppCompatEditText, View.OnTouchListener {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Masukkan Nama anda"

        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val nama = s.toString()
                if (nama.length < 4 && nama != "") {
                    error = "Nama harus memiliki setidaknya 4 karakter"
                    setError(error, null)
                } else {
                    error = null
                }
            }
        })

        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }
}
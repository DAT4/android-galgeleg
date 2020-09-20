package sh.mama.galgeleg

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import sh.mama.galgeleg.models.Word

class MainActivity : AppCompatActivity() {
    fun EditText.limitLength(maxLength: Int) {
        filters = arrayOf(InputFilter.LengthFilter(maxLength))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val word = Word("santa")

        updateWord(word)

        for (option in word.options) {
            val key = Button(this)
            key.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            key.layoutParams.width = 100
            key.text = option.toString()
            key.isActivated = option.toString() != " "
            key.setOnClickListener {
                if (it.isActivated) {
                    word.guess(option)
                    key.text = ""
                    it.isActivated = false
                    updateWord(word)
                } else {
                    Toast.makeText(this, "Its not a choice!", Toast.LENGTH_SHORT).show()
                }
            }

            buttons.addView(key)
        }
    }

    fun updateWord(word: Word) {
        bogstaver.removeAllViews()
        for (letter in word.letters) {
            val x = TextView(this)
            x.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            x.layoutParams.width = 100
            x.textSize = 34F

            if (letter.isGuessed()) {
                x.text = letter.toString()
            } else {
                x.text = "_"
                x.isActivated = false
            }

            bogstaver.addView(x)
        }
    }
}


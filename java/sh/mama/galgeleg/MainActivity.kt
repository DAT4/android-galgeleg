package sh.mama.galgeleg

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.*
import androidx.core.view.marginBottom
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
        printMan(word)
        generateKeyboard(word)

    }

    fun generateKeyboard(word: Word) {
        for (option in word.options) {
            val key = Button(this)
            key.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            key.layoutParams.width = 150
            key.setTextSize(24F)
            key.text = option.toString()
            key.isActivated = option.toString() != " "
            key.setOnClickListener {
                if (it.isActivated) {
                    if (!word.guess(option)) {
                        lost(word)
                    } else {
                        key.text = ""
                        it.isActivated = false
                        updateWord(word)
                    }
                    printMan(word)
                } else {
                    Toast.makeText(this, "Its not a choice!", Toast.LENGTH_SHORT).show()
                }
            }
            buttons.addView(key)
        }
    }

    fun printMan(word: Word) {
        hangman.removeAllViews()
        val x = TextView(this)
        x.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        x.setTextSize(34F)
        x.setTextColor(Color.BLUE)
        x.text = word.hangman.getState()
        hangman.addView(x)
    }

    fun lost(word: Word) {
        bogstaver.removeAllViews()
        for (letter in word.letters) {
            val x = TextView(this)
            x.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            x.layoutParams.width = 100
            x.textSize = 34F
            x.setTextColor(Color.RED)
            x.text = letter.toString()
            bogstaver.addView(x)
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


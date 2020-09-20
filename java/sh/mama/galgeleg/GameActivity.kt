package sh.mama.galgeleg

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.InputFilter
import android.widget.*
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.mama.galgeleg.models.Word

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
            key.isActivated = true
            key.text = option.toString()
            key.setOnClickListener {
                if (it.isActivated) {
                    if (!word.guess(option)) {
                        lost(word)
                        printMan(word)
                        Toast.makeText(this,"YOU LOST!",Toast.LENGTH_LONG).show()
                    } else {
                        key.text = ""
                        it.isActivated = false
                        updateWord(word)
                        if(word.done()){
                            won(word)
                        } else {
                            printMan(word)
                        }
                    }
                } else {
                    Toast.makeText(this, "Its not a choice!", Toast.LENGTH_SHORT).show()
                }
            }
            buttons.addView(key)
        }
    }

    fun printMan(word: Word) {
        state.setImageResource(word.hangman.getState())
    }

    fun restart(){
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(5000)
            state.setImageResource(R.drawable.arrow)
        }
        state.setOnClickListener{
            val main = Intent(this,MainActivity::class.java)
            startActivity(main)
        }
    }

    fun won(word: Word) {
        bogstaver.removeAllViews()
        for (letter in word.letters) {
            val x = TextView(this)
            x.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            x.layoutParams.width = 100
            x.textSize = 34F
            x.setTextColor(Color.GREEN)

            if (letter.isGuessed()) {
                x.text = letter.toString()
            } else {
                x.text = "_"
                x.isActivated = false
            }

            bogstaver.addView(x)
            state.setImageResource(R.drawable.win)
            Toast.makeText(this,"YOU WON!",Toast.LENGTH_LONG).show()
            restart()
        }
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

            if (letter.isGuessed()) {
                x.text = letter.toString()
            } else {
                x.text = "_"
                x.isActivated = false
            }

            bogstaver.addView(x)
            restart()
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


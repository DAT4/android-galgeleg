package sh.mama.hangman

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sh.mama.hangman.models.Game
import sh.mama.hangman.models.Letter

class GameActivity : AppCompatActivity() {
    private val letters = ArrayList<TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val word = intent.getStringExtra("word")
        if (word == null) {
            finish()
        } else {
            val game = Game(word)
            createWord(game)
            printMan(game)
            generateKeyboard(game)
        }


    }

    private fun createWord(game: Game) {
        for (letter in game.letters) {
            val x = TextView(this)
            x.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            x.layoutParams.width = 100
            x.textSize = 34F
            x.text = "_"
            x.isActivated = false
            letters.add(x)
            bogstaver.addView(x)
        }
    }

    private fun updateWord(game: Game) {
        game.letters.forEachIndexed { i, letter ->
            if (letter.isGuessed()) {
                letters[i].text = game.letters[i].toString()
            }
        }
    }

    private fun printMan(game: Game) {
        state.setImageResource(game.hangman.getState())
    }

    private fun generateKeyboard(game: Game) {
        for (option in game.options) {
            val button = Button(this)
            button.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            button.layoutParams.width = 150
            button.setTextSize(24F)
            button.isActivated = true
            button.text = option.toString()
            button.setOnClickListener {
                guess(button, game, option)
            }
            buttons.addView(button)
        }
    }

    private fun guess(button: Button, game: Game, letter: Letter) {
        if (button.isActivated) {
            if (!game.guess(letter)) {
                lost(game)
            } else {
                button.text = ""
                button.isActivated = false
                updateWord(game)
                if (game.isDone()) {
                    won(game)
                } else {
                    printMan(game)
                }
            }
        } else {
            Toast.makeText(this, "Its not a choice!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun won(game: Game) {
        Toast.makeText(this, "YOU WON!", Toast.LENGTH_LONG).show()
        game.letters.forEachIndexed { i, letter ->
            letters[i].setTextColor(Color.GREEN)
            letters[i].text = game.letters[i].toString()
        }
        state.setImageResource(R.drawable.win)
        restart()
    }

    private fun lost(game: Game) {
        printMan(game)
        Toast.makeText(this, "YOU LOST!", Toast.LENGTH_LONG).show()
        game.letters.forEachIndexed { i, letter ->
            if (!letter.isGuessed()) {
                letters[i].setTextColor(Color.RED)
                letters[i].text = game.letters[i].toString()
            }
        }
        restart()
    }

    private fun restart() {
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(3 * 1000)
            state.setImageResource(R.drawable.arrow)
        }
        state.setOnClickListener {
            finish()
        }
    }
}


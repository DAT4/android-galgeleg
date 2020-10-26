package sh.mama.hangman.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import kotlinx.android.synthetic.main.activity_game.*
import sh.mama.hangman.R
import sh.mama.hangman.models.Game
import sh.mama.hangman.models.Letter
import sh.mama.hangman.models.Word
import androidx.appcompat.app.AlertDialog as AlertDialog

class GameActivity : AppCompatActivity() {
    private lateinit var game: Game
    private val letters = ArrayList<TextView>()
    private lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val word = intent.getSerializableExtra("word") as Word
        word.make()
        game = Game(word)
        createWord()
        printMan()
        generateKeyboard()
        countDownTimer = object: CountDownTimer(60*1000*5,1000) {
            override fun onTick(p0: Long) {
                val minutes = p0 / 1000 / 60
                val seconds = p0 / 1000 % 60
                if (seconds > 9) {
                    game_time.text = "0$minutes:$seconds"
                } else {
                    game_time.text = "0$minutes:0$seconds"
                }
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
        var hintcounter = 1
        game_hint.setOnClickListener {
            val des = AlertDialog.Builder(this)
            des.setTitle("HINT $hintcounter")
            if (hintcounter == 1)
                des.setMessage(game.word.hint1)
            if (hintcounter == 2)
                des.setMessage(game.word.hint2)
            if (hintcounter == 3)
                des.setMessage(game.word.hint3)
            des.setPositiveButton("OK") {a,b ->}
            des.show()
            hintcounter++
        }
    }

    private fun createWord() {
        for (letter in game.word.letters) {
            val x = TextView(this)
            x.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            x.layoutParams.width = 75
            x.textSize = 24F
            x.text = "_"
            x.isActivated = false
            letters.add(x)
            bogstaver.addView(x)
        }
    }

    private fun updateWord() {
        game.word.letters.forEachIndexed { i, letter ->
            if (letter.isGuessed()) {
                letters[i].text = game.word.letters[i].toString()
            }
        }
    }

    private fun printMan() {
        state.setImageResource(game.hangman.getState())
    }

    private fun generateKeyboard() {
        for (option in game.options) {
            val button = createButton(option.toString(), 150) { guess(it, option) }
            buttons.addView(button)
        }
    }

    private fun guess(button: Button, letter: Letter) {
        if (button.isActivated) {
            val stillAlive = game.guess(letter)
            if (!stillAlive) {
                lost()
            } else {
                button.text = ""
                button.isActivated = false
                updateWord()
                if (game.isDone()) {
                    won()
                } else {
                    printMan()
                }
            }
        } else {
            Toast.makeText(this, "Its not a choice!", Toast.LENGTH_SHORT).show()
        }
    }

    fun createButton(title: String, width: Int, function: (button: Button) -> Unit): Button {
        val button = Button(this)
        button.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        button.layoutParams.width = width
        button.textSize = 24F
        button.text = title
        button.isActivated = true
        button.setOnClickListener { function(button) }
        return button
    }

    private fun endGame() {
        val des = AlertDialog.Builder(this)
        des.setTitle(game.word.word)
        des.setMessage(game.word.description)
        des.setPositiveButton("Restart") {a,b -> finish() }
        des.show()
    }

    private fun won() {
        endGame()
        game.word.letters.forEachIndexed { i, letter ->
            letters[i].setTextColor(Color.GREEN)
            letters[i].text = game.word.letters[i].toString()
        }
        state.setImageResource(R.drawable.win)
    }

    private fun lost() {
        endGame()
        game.word.letters.forEachIndexed { i, letter ->
            if (!letter.isGuessed()) {
                letters[i].setTextColor(Color.RED)
                letters[i].text = game.word.letters[i].toString()
            }
        }
        printMan()
    }
}


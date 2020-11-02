package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import sh.mama.hangman.R
import sh.mama.hangman.models.Game
import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Letter
import sh.mama.hangman.models.Word

class PlayGameActivity : AppCompatActivity() {
    private lateinit var game: Game
    private lateinit var name: String
    private var time = 0
    private var hintCounter = 1
    private val letters = ArrayList<TextView>()
    private lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val word = intent.getSerializableExtra("word") as Word
        this.name = intent.getSerializableExtra("name") as String
        word.make()
        game = Game(word)
        createWord()
        state.setImageResource(game.hangman.getState())
        generateKeyboard()

        countDownTimer = object : CountDownTimer(60 * 1000 * 5, 1000) {
            override fun onTick(p0: Long) {
                time = (p0/1000).toInt()
                val minutes = p0 / 1000 / 60
                val seconds = p0 / 1000 % 60
                if (seconds > 9) {
                    game_time.text = "0$minutes:$seconds"
                } else {
                    game_time.text = "0$minutes:0$seconds"
                }
            }

            override fun onFinish() {
                endGame(false)
            }
        }.start()

        game_hint.setOnClickListener {
            val des = AlertDialog.Builder(this)
            des.setTitle("HINT $this.hintCounter")
            if (this.hintCounter == 1)
                des.setMessage(game.word.hint1)
            if (this.hintCounter == 2)
                des.setMessage(game.word.hint2)
            if (this.hintCounter == 3)
                des.setMessage(game.word.hint3)
            des.setPositiveButton("OK") { a, b -> }
            des.show()
            hintCounter++
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

    private fun generateKeyboard() {
        for (option in game.options) {
            val button = createButton(option.toString()) { guess(it, option) }
            buttons.addView(button)
        }
    }

    private fun guess(button: Button, letter: Letter) {
        if (button.isActivated) {
            val stillAlive = game.guess(letter)
            if (!stillAlive) {
                endGame(false)
            } else {
                button.text = ""
                button.isActivated = false
                updateWord()
                if (game.isDone()) {
                    endGame(true)
                } else {
                    state.setImageResource(game.hangman.getState())
                }
            }
        } else {
            Toast.makeText(this, "Its not a choice!", Toast.LENGTH_SHORT).show()
        }
    }

    private val buttonClick = AlphaAnimation(1F, 0.8F)
    private fun createButton(title: String, function: (button: Button) -> Unit): Button {
        val inflater = LayoutInflater.from(this)
        val button = inflater.inflate(R.layout.button_letter, null, false) as Button
        button.text = title
        button.isActivated = true
        button.setOnClickListener {
            button.startAnimation(buttonClick)
            function(button)
        }
        return button
    }

    private fun endGame(won: Boolean) {
        val score = HighScore(null, name, this.time, this.hintCounter, this.game.word)
        val intent = Intent(this, EndGameActivity::class.java)
        intent.putExtra("won", won)
        intent.putExtra("word", this.game.word)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
    }
}


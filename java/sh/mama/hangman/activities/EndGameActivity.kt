package sh.mama.hangman.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_end_game.*
import sh.mama.hangman.R
import sh.mama.hangman.models.Word
import kotlin.system.exitProcess

class EndGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)
        val won = intent.getSerializableExtra("won") as Boolean
        val word = intent.getSerializableExtra("word") as Word

        word_word.text = word.word
        if (won) {
            word_word.setTextColor(Color.GREEN)
            state_image.setImageResource(R.drawable.win)
            word_description.text = word.description
        } else {
            word_word.setTextColor(Color.RED)
            state_image.setImageResource(R.drawable.g)
            word_description.text = "You need to win do get the description."
        }
    }

    private fun end() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}


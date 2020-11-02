package sh.mama.hangman.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sh.mama.hangman.R
import sh.mama.hangman.models.Word

class HighScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        val win = intent.getSerializableExtra("win") as Boolean
        val word = intent.getSerializableExtra("word") as Word
    }
}


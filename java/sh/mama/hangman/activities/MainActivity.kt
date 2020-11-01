package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sh.mama.hangman.R
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play.setOnClickListener {
            val game = Intent(this, PickCategoryActivity::class.java)
            game.putExtra("edit", false)
            startActivity(game)
        }
        create_categories.setOnClickListener {
            val create = Intent(this, PickCategoryActivity::class.java)
            create.putExtra("edit", true)
            startActivity(create)
        }
        watch_highscores.setOnClickListener {
            val watchHighScores = Intent(this, HighScoreActivity::class.java)
            startActivity(watchHighScores)
        }
        add_word_button.setOnClickListener {
            val addWord = Intent(this, EditWordsActivity::class.java)
            addWord.putExtra("create", true)
            startActivity(addWord)
        }
        exit.setOnClickListener {
            end()
        }
    }

    private fun end() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}


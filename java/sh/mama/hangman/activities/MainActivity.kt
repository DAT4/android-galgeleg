package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sh.mama.hangman.R
import sh.mama.hangman.Enumerators.ActionType
import sh.mama.hangman.libs.DataGetter
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val buttonClick = AlphaAnimation(1F, 0.1F)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataGetter.getHighScores()
        DataGetter.getWords()

        play.setOnClickListener {
            it.startAnimation(buttonClick)
            val game = Intent(this, PickCategoryActivity::class.java)
            game.putExtra("actionType", ActionType.GAME)
            startActivity(game)
        }
        create_categories.setOnClickListener {
            it.startAnimation(buttonClick)
            val create = Intent(this, PickCategoryActivity::class.java)
            create.putExtra("actionType", ActionType.EDIT)
            startActivity(create)
        }
        watch_highscores.setOnClickListener {
            it.startAnimation(buttonClick)
            val watchHighScores = Intent(this, HighScoreActivity::class.java)
            startActivity(watchHighScores)
        }
        add_word_button.setOnClickListener {
            it.startAnimation(buttonClick)
            val addWord = Intent(this, EditWordActivity::class.java)
            addWord.putExtra("create", true)
            startActivity(addWord)
        }
        exit.setOnClickListener {
            it.startAnimation(buttonClick)
            end()
        }
    }

    private fun end() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}


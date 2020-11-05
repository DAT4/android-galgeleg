package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.R
import sh.mama.hangman.enumerators.ActionType
import sh.mama.hangman.libs.DataGetter.getStuff
import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word
import sh.mama.hangman.observer.ConcreteScores.setHighScores
import sh.mama.hangman.observer.ConcreteWords.setWords
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val buttonClick = AlphaAnimation(1F, 0.1F)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.IO){
            val wordType = object : TypeToken<List<Word>>() {}.type
            val highScoreType = object : TypeToken<List<HighScore>>() {}.type
            val highScores:List<HighScore> = getStuff("https://mama.sh/hangman/api/highscores",highScoreType)
            val words:List<Word> = getStuff("https://mama.sh/hangman/api",wordType)
            launch (Dispatchers.Main){
                setHighScores(highScores as MutableList<HighScore>)
                setWords(words as MutableList<Word>)
            }
        }

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


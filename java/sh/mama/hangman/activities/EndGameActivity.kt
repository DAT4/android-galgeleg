package sh.mama.hangman.activities

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_end_game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.observer.ConcreteScores
import sh.mama.hangman.observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.adapters.HighScoreAdapter
import sh.mama.hangman.libs.DataGetter.getStuff
import sh.mama.hangman.libs.DataGetter.updateStuff
import sh.mama.hangman.libs.RequestType
import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word
import sh.mama.hangman.observer.ConcreteScores.setHighScores
import kotlin.system.exitProcess

class EndGameActivity : AppCompatActivity(), IObserver {
    private lateinit var word: Word

    override fun update() {
        printScore()
    }

    override fun onDestroy() {
        super.onDestroy()
        ConcreteScores.remove(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)
        val won = intent.getSerializableExtra("won") as Boolean
        val score = intent.getSerializableExtra("score") as HighScore?
        this.word = intent.getSerializableExtra("word") as Word

        ConcreteScores.add(this)

        word_word.text = word.word

        play_again_btn.setOnClickListener {
            finish()
        }

        printScore()

        if (won) {
            word_word.setTextColor(Color.GREEN)
            state_image.setImageResource(R.drawable.win)
            word_description.text = word.description
            if (score != null){
                GlobalScope.launch(Dispatchers.IO){
                    val url = "https://mama.sh/hangman/api/highscores"
                    val type = object : TypeToken<List<HighScore>>() {}.type
                    updateStuff(score,url,RequestType.POST)
                    val highScores:MutableList<HighScore> = getStuff(url, type)
                    launch(Dispatchers.Main){
                        setHighScores(highScores)
                    }
                }

            }

        } else {
            word_word.setTextColor(Color.RED)
            state_image.setImageResource(R.drawable.h)
            word_description.text = "You need to win do get the description."
            word_description.gravity = Gravity.CENTER_HORIZONTAL
        }

    }

    private fun printScore() {
        val adapter = HighScoreAdapter(ConcreteScores.getHighScoreFromWord(this.word), false)
        highscore_list.adapter = adapter
        highscore_list.layoutManager = LinearLayoutManager(this)
    }

    private fun end() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}


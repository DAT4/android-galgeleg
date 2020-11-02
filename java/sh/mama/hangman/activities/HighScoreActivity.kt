package sh.mama.hangman.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_end_game.*
import sh.mama.hangman.Observer.ConcreteScores
import sh.mama.hangman.R
import sh.mama.hangman.adapters.HighScoreAdapter

class HighScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        printScore()
    }

    private fun printScore() {
        val adapter = HighScoreAdapter(ConcreteScores.getOverAllHighScores(), true)
        highscore_list.adapter = adapter
        highscore_list.layoutManager = LinearLayoutManager(this)
    }
}


package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_end_game.highscore_list
import kotlinx.android.synthetic.main.activity_highscore.*
import sh.mama.hangman.observer.ConcreteScores
import sh.mama.hangman.R
import sh.mama.hangman.enumerators.ActionType
import sh.mama.hangman.adapters.HighScoreAdapter
import sh.mama.hangman.observer.IObserver

class HighScoreActivity : AppCompatActivity(),IObserver {
    private var global = true
    private var categoryTitle: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConcreteScores.add(this)
        setContentView(R.layout.activity_highscore)
        this.categoryTitle = intent.getStringExtra("category")
        if (categoryTitle != null) {
            this.global = false
            headline.text = categoryTitle
            category_btn.visibility = View.GONE
        } else {
            category_btn.setOnClickListener {
                val intent = Intent(this, PickCategoryActivity::class.java)
                intent.putExtra("actionType", ActionType.HIGHSCORES)
                startActivity(intent)
            }
        }
        printScore()
    }

    private fun printScore() {
        val highScores = if (global) {
            ConcreteScores.getOverAllHighScores()
        } else {
            ConcreteScores.getHighScoreFromCategory(this.categoryTitle!!)
        }
        val adapter = HighScoreAdapter(highScores, true)
        highscore_list.adapter = adapter
        highscore_list.layoutManager = LinearLayoutManager(this)
    }

    override fun update() {
        printScore()
    }

    override fun onDestroy() {
        super.onDestroy()
        ConcreteScores.remove(this)
    }
}


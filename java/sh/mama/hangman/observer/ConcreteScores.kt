package sh.mama.hangman.observer

import com.google.gson.reflect.TypeToken
import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word
import java.lang.reflect.Type

object ConcreteScores : ICache {
    override val url: String = "https://mama.sh/hangman/api/highscores"
    override val type: Type = object : TypeToken<ArrayList<HighScore>>() {}.type

    override var content: ArrayList<*> = ArrayList<HighScore>()
    override val observers: ArrayList<IObserver> = ArrayList()

    fun getHighScoreFromWord(word: Word): List<HighScore> {
        val scores: ArrayList<HighScore> = ArrayList()
        this.content.forEach { score ->
            if (score is HighScore)
                if (score.word.ID == word.ID) {
                    scores.add(score)
                }
        }
        return order(scores)
    }


    fun getHighScoreFromCategory(category: String): List<HighScore> {
        val scores: ArrayList<HighScore> = ArrayList()
        this.content.forEach { score ->
            if (score is HighScore)
                if (score.word.category == category) {
                    scores.add(score)
                }
        }
        return order(scores)
    }

    fun getOverAllHighScores(): List<HighScore> {
        return order(this.content as ArrayList<HighScore>)
    }

    fun setHighScores(scores: ArrayList<HighScore>) {
        this.content = scores
        sendUpdateEvent()
    }

    private fun order(scores: ArrayList<HighScore>): List<HighScore> {
        scores.sortWith(kotlin.Comparator { lhs, rhs ->
            when {
                lhs.getScore() > rhs.getScore() -> -1
                lhs.getScore() < rhs.getScore() -> 1
                else -> 0
            }
        })

        return if (scores.size > 10) {
            scores.slice(0 until 10)
        } else {
            scores
        }
    }
}
package sh.mama.hangman.Observer

import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word

object ConcreteScores : IObservable {
    private var highscores: MutableList<HighScore> = ArrayList()
    override val observers: ArrayList<IObserver> = ArrayList()

    fun getHighScoreFromWord(word: Word): List<HighScore> {
        val scores: ArrayList<HighScore> = ArrayList()
        this.highscores.forEach { score ->
            if (score.word.ID == word.ID) {
                scores.add(score)
            }
        }
        return order(scores)
    }


    fun getHighScoreFromCategory(category: String): List<HighScore> {
        val scores: ArrayList<HighScore> = ArrayList()
        this.highscores.forEach { score ->
            if (score.word.category == category) {
                scores.add(score)
            }
        }
        return order(scores)
    }

    fun getOverAllHighScores(): List<HighScore> {
        return order(this.highscores as ArrayList<HighScore>)
    }

    fun setHighScores(scores: MutableList<HighScore>) {
        this.highscores = scores
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
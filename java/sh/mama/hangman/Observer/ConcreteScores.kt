package sh.mama.hangman.Observer

import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word

object ConcreteScores : IObservable {
    private var highscores: MutableList<HighScore> = ArrayList()
    override val observers: ArrayList<IObserver> = ArrayList()

    fun isNull(): Boolean {
        return this.highscores.isEmpty()
    }

    fun getHighScoreFromWord(word: Word): List<HighScore> {
        val scores: ArrayList<HighScore> = ArrayList()
        this.highscores.forEach { score ->
            if (score.word.ID == word.ID) {
                scores.add(score)
            }
        }
        scores.sortWith(kotlin.Comparator { lhs, rhs ->
            when {
                lhs.getScore() > rhs.getScore() -> -1
                lhs.getScore() < rhs.getScore() -> 1
                else -> 0
            }
        })
        return scores
    }

    fun getHighScoreFromCategory(category: String): List<HighScore> {
        val scores: ArrayList<HighScore> = ArrayList()
        this.highscores.forEach { score ->
            if (score.word.category == category) {
                scores.add(score)
            }
        }
        return scores
    }

    fun setHighScores(scores: MutableList<HighScore>) {
        this.highscores = scores
        sendUpdateEvent()
    }
}
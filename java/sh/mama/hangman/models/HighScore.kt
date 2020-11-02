package sh.mama.hangman.models

import java.io.Serializable

data class HighScore(
    val ID: String?,
    val player: String,
    val time: Int,
    val hints: Int,
    val word:Word
) : Serializable {
    fun getScore():Int {
        return time/1000/hints*word.difficulty
    }
}


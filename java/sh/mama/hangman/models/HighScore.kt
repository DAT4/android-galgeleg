package sh.mama.hangman.models

import java.io.Serializable

data class HighScore(
    val ID: String?,
    val player: String,
    val time: Int,
    val hints: Int,
    val wrongs: Int,
    val word:Word
) : Serializable {
    fun getScore():Int {
        return time*word.difficulty/hints/(wrongs+1)
    }
}


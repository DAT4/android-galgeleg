package sh.mama.hangman.models

import java.io.Serializable

data class Letter(
    var letter: Char,
    var guessed: Boolean = false
) : Serializable {

    init {
        letter = letter.toUpperCase()
        if (letter == ' ') {
            this.guessed = true
        }
    }

    fun isGuessed(): Boolean {
        return this.guessed
    }

    fun guess(letter: Letter): Boolean {
        return if (letter.letter == this.letter) {
            this.guessed = true
            true
        } else {
            false
        }
    }

    override fun toString(): String {
        return this.letter.toString()
    }
}

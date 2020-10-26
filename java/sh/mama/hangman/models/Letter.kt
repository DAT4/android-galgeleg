package sh.mama.hangman.models

class Letter(letter: Char) {
    private val letter = letter.toUpperCase()
    private var guessed = false

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

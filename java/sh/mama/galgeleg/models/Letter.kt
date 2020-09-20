package sh.mama.galgeleg.models

class Letter(letter: Char) {
    private val letter = letter.toUpperCase()
    private var guessed = false

    fun isGuessed(): Boolean {
        return this.guessed
    }

    fun guess(letter: Letter): Boolean {
        if (letter.letter == this.letter) {
            this.guessed = true
            return true
        } else {
            return false
        }
    }

    override fun toString(): String{
        return this.letter.toString()
    }
}

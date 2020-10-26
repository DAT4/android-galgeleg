package sh.mama.hangman.models

import java.io.Serializable

data class Word(
    var word: String = "",
    var difficulty: Int = 1,
    var description: String = "",
    var hint1: String = "",
    var hint2: String = "",
    var hint3: String = "",
    var category: String = ""
) : Serializable {
    lateinit var letters: List<Letter>
    fun make() {
        this.letters = this.word.toList().map { Letter(it) }
    }
}
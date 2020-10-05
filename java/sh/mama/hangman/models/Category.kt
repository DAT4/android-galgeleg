package sh.mama.hangman.models

import java.io.Serializable

data class Category(var title: String, val words: ArrayList<Word>): Serializable {

    fun getOne(): Word {
        return this.words[this.words.indices.random()]
    }
}

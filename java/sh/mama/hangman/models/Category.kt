package sh.mama.hangman.models

data class Category(val title: String, val words: ArrayList<Word>) {

    data class Word(
        val word: String,
        val difficulty: Int,
        val description: String,
        val leads: List<String>
    )

    fun add(word: String) {
        this.words.add(Word(word, 0, "", listOf("")))
    }

    fun getOne(): String {
        return this.words[this.words.indices.random()].word
    }
}

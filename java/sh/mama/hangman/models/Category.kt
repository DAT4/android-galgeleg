package sh.mama.hangman.models

data class Category(val title: String) {
    private val words = ArrayList<String>()

    fun add(word: String) {
        this.words.add(word)
    }

    fun getOne(): String {
        return this.words[(0 until this.words.size).random()]
    }
}

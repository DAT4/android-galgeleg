package sh.mama.hangman.Observer

import sh.mama.hangman.models.Category
import sh.mama.hangman.models.Word

object wordsHolder : IObservable {
    private var words: MutableList<Word> = ArrayList()
    override val observers: ArrayList<IObserver> = ArrayList()

    fun isNull(): Boolean{
        return this.words.isEmpty()
    }

    fun deleteWord(word: Word) {
        this.words.remove(word)
        sendUpdateEvent()
    }

    fun addWord(word: Word) {
        this.words.add(word)
        sendUpdateEvent()
    }

    fun getWords(): MutableList<Word> {
        return this.words
    }

    fun getCategories(): MutableList<Category> {
        val categories: MutableList<Category> = ArrayList()
        this.words.forEach { word ->
            var are = false
            categories.forEach { category ->
                if (word.category == category.title) {
                    are = true
                    category.words.add(word)
                }
            }
            if (!are) {
                categories.add(Category(word.category, arrayListOf(word)))
            }
        }
        return categories
    }

    fun setWords(words: MutableList<Word>) {
        this.words = words
        sendUpdateEvent()
    }
}
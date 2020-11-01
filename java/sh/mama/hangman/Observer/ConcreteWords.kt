package sh.mama.hangman.Observer

import sh.mama.hangman.models.Category
import sh.mama.hangman.models.Word

object ConcreteWords : IObservable {
    private var words: MutableList<Word> = ArrayList()
    override val observers: ArrayList<IObserver> = ArrayList()

    fun isNull(): Boolean{
        return this.words.isEmpty()
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

    fun getCategory(title: String): Category {
        val category : Category = Category(title,ArrayList())
        this.words.forEach { word ->
            if (word.category == category.title) {
                    category.words.add(word)
                }
        }
        return category
    }

    fun setWords(words: MutableList<Word>) {
        this.words = words
        sendUpdateEvent()
    }
}
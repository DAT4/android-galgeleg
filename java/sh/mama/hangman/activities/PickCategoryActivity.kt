package sh.mama.hangman.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_pick_context.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.observer.ConcreteWords
import sh.mama.hangman.observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.enumerators.ActionType
import sh.mama.hangman.adapters.CategoryAdapter
import sh.mama.hangman.libs.DataGetter.getStuff
import sh.mama.hangman.models.Category
import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word
import sh.mama.hangman.observer.ConcreteWords.setWords

class PickCategoryActivity : AppCompatActivity(), IObserver {
    private lateinit var actionType: ActionType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)
        this.actionType = intent.getSerializableExtra("actionType") as ActionType
        ConcreteWords.add(this)
        if (ConcreteWords.isNull()) {
            GlobalScope.launch(Dispatchers.IO){
                val wordType = object : TypeToken<List<Word>>() {}.type
                val words:MutableList<Word> = getStuff("https://mama.sh/hangman/api",wordType)
                launch(Dispatchers.Main){
                    setWords(words)
                }
            }

        } else {
            printButtons(ConcreteWords.getCategories())
        }
    }

    override fun update() {
        printButtons(ConcreteWords.getCategories())
    }

    override fun onDestroy() {
        super.onDestroy()
        ConcreteWords.remove(this)
    }

    private fun printButtons(data: List<Category>) {
        val adapter = CategoryAdapter(data, this.actionType)
        categories.adapter = adapter
        categories.layoutManager = LinearLayoutManager(this)
    }
}
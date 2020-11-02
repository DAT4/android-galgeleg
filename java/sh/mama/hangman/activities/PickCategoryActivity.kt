package sh.mama.hangman.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pick_context.*
import sh.mama.hangman.Observer.ConcreteWords
import sh.mama.hangman.Observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.adapters.ActionType
import sh.mama.hangman.adapters.CategoryAdapter
import sh.mama.hangman.libs.DataGetter.getWords
import sh.mama.hangman.models.Category

class PickCategoryActivity : AppCompatActivity(), IObserver {
    private lateinit var actionType: ActionType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)
        this.actionType = intent.getSerializableExtra("actionType") as ActionType
        ConcreteWords.add(this)
        if (ConcreteWords.isNull()) {
            getWords()
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
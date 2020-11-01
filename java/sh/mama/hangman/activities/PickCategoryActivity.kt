package sh.mama.hangman.activities

import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pick_context.*
import sh.mama.hangman.Observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.adapters.CategoryAdapter
import sh.mama.hangman.libs.DataGetter.getWords
import sh.mama.hangman.models.Category
import sh.mama.hangman.Observer.ConcreteWords

class PickCategoryActivity : AppCompatActivity(), IObserver {
    private var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)
        this.edit = intent.getSerializableExtra("edit") as Boolean
        ConcreteWords.add(this)
        if (ConcreteWords.isNull()){
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
        val adapter = CategoryAdapter(data, this.edit)
        categories.adapter = adapter
        categories.layoutManager = LinearLayoutManager(this)
    }

}
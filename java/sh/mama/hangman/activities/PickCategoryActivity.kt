package sh.mama.hangman.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pick_context.*
import sh.mama.hangman.Observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.adapters.CategoryAdapter
import sh.mama.hangman.libs.DataGetter.getCategories
import sh.mama.hangman.models.Category
import sh.mama.hangman.Observer.wordsHolder

class PickCategoryActivity : AppCompatActivity(), IObserver {
    private var edit = false
    private var needUpdate = false
    var isOnScreen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)
        this.edit = intent.getSerializableExtra("edit") as Boolean
        wordsHolder.add(this)
        isOnScreen = true
        if (wordsHolder.isNull()){
            getCategories()
        } else {
            printButtons(wordsHolder.getCategories())
        }
    }

    override fun update() {
        if (isOnScreen){
            printButtons(wordsHolder.getCategories())
        }
        else
            this.needUpdate = true
    }

    override fun onPause() {
        super.onPause()
        isOnScreen = false
    }

    override fun onResume() {
        super.onResume()
        isOnScreen = true
        if(needUpdate){
            printButtons(wordsHolder.getCategories())
        }
    }

    private fun printButtons(data: List<Category>) {
        val adapter = CategoryAdapter(data, this.edit)
        categories.adapter = adapter
        categories.layoutManager = LinearLayoutManager(this)
    }

}
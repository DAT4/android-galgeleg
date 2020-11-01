package sh.mama.hangman.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_context.*
import sh.mama.hangman.Observer.IObserver
import sh.mama.hangman.Observer.ConcreteWords
import sh.mama.hangman.Observer.ConcreteWords.getCategory
import sh.mama.hangman.R
import sh.mama.hangman.adapters.WordAdapter
import sh.mama.hangman.models.Category
import sh.mama.hangman.models.Word

class EditCategoryActivity : AppCompatActivity(), IObserver {
    private lateinit var category: Category

    override fun update() {
        printButtons(getCategory(this.category.title).words)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_context)
        ConcreteWords.add(this)
        this.category = intent.getSerializableExtra("category") as Category
        abort.setOnClickListener { finish() }
        create_context.setOnClickListener {
            val create = Intent(this, EditWordsActivity::class.java)
            create.putExtra("word", Word(category = category.title))
            create.putExtra("create", true)
            startActivity(create)
        }
        printButtons(category.words)
    }

    override fun onDestroy() {
        super.onDestroy()
        ConcreteWords.remove(this)
    }

    private fun printButtons(data: MutableList<Word>) {
        val adapter = WordAdapter(data)
        word_list.adapter = adapter
        word_list.layoutManager = LinearLayoutManager(this)
    }

    private fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
                func()
            }
            true
        }
    }

}
package sh.mama.hangman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_create_context.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.models.Category
import sh.mama.hangman.models.Word
import java.net.URL

class EditContextActivity : AppCompatActivity() {
    private lateinit var category: Category
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_context)
        this.category = intent.getSerializableExtra("category") as Category
        abort.setOnClickListener { finish() }
        create_context.setOnClickListener {
            val create = Intent(this,AddWordsActivity::class.java)
            create.putExtra("word",Word(category = category.title))
            create.putExtra("create", true)
            startActivity(create)
        }
        printButtons(category.words)
    }

    private fun printButtons(data: List<Word>) {
        for (word in data) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = word.word
            button.setOnClickListener {
                val editWord = Intent(this, AddWordsActivity::class.java)
                editWord.putExtra("word", word)
                editWord.putExtra("create", false)
                startActivity(editWord)
            }
            word_list.addView(button)
        }
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


    private fun makeCategory() {
        GlobalScope.launch(Dispatchers.IO) {
            val data = URL("https://mama.sh/hangman/api").readText()
        }
    }

    private fun parseShit(data: String): List<Category> {
        println(data)
        val gson = Gson()
        val categoriesType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(data, categoriesType)
    }


}
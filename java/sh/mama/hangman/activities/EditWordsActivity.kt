package sh.mama.hangman.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_add_words.*
import sh.mama.hangman.Observer.IObserver
import sh.mama.hangman.Observer.wordsHolder
import sh.mama.hangman.R
import sh.mama.hangman.libs.DataGetter
import sh.mama.hangman.models.Word

class EditWordsActivity : AppCompatActivity(), IObserver {
    private lateinit var word: Word
    private var creating = false

    override fun update() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_words)
        val data = intent.getSerializableExtra("word") as Word?
        if (data != null) {
            this.word = data
        }
        this.creating = intent.getSerializableExtra("create") as Boolean

        wordsHolder.add(this)

        if (this.creating) {
            word_delete.visibility = View.GONE
        } else {
            word_delete.isActivated = true
            word_delete.setOnClickListener {
                if (it.isActivated) {
                    DataGetter.updateWord(this.word, "DELETE")
                    it.isActivated = false
                } else {
                    Toast.makeText(this, "You already clicked the button.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        createButtons()

        word_add.isActivated = true
        word_add.setOnClickListener {
            if (it.isActivated) {
                if (creating) {
                    updateFromFields()
                    DataGetter.updateWord(this.word, "POST")
                } else {
                    updateFromFields()
                    DataGetter.updateWord(this.word, "PUT")
                }

            } else {
                Toast.makeText(this, "You already clicked the button.", Toast.LENGTH_SHORT).show()
            }
            it.isActivated = false
        }
        word_abort.setOnClickListener { finish() }
        fillData(this.word)
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

    private fun updateFromFields() {
        this.word.word = word_word.text.toString()
        this.word.category = word_category.text.toString()
        this.word.description = word_description.text.toString()
        this.word.hint1 = word_hint1.text.toString()
        this.word.hint2 = word_hint2.text.toString()
        this.word.hint3 = word_hint3.text.toString()
    }

    private fun fillData(word: Word) {
        word_word.setText(word.word)
        word_description.setText(word.description)
        word_hint1.setText(word.hint1)
        word_hint2.setText(word.hint2)
        word_hint3.setText(word.hint3)
        word_category.setText(word.category)
        val button = word_difficulty[word.difficulty - 1] as Button
        button.setBackgroundColor(Color.GRAY)
    }

    private fun createButtons() {
        for (i in 1..10) {
            val button = Button(this)
            button.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = "$i"
            button.layoutParams.height = 100
            button.layoutParams.width = 100
            button.setTextColor(Color.GREEN)
            button.textSize = 10F
            button.setBackgroundColor(Color.BLACK)
            button.setOnClickListener {
                word_difficulty.forEach { that ->
                    that.setBackgroundColor(Color.BLACK)
                }
                it.setBackgroundColor(Color.GRAY)
                this.word.difficulty = i
            }
            word_difficulty.addView(button)
        }
    }
}
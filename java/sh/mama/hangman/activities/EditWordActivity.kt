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
import sh.mama.hangman.Observer.ConcreteWords
import sh.mama.hangman.Observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.libs.DataGetter
import sh.mama.hangman.models.Word

class EditWordActivity : AppCompatActivity(), IObserver {
    private var creating = false
    private var difficulty = 1

    override fun update() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        ConcreteWords.remove(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_words)
        var word = intent.getSerializableExtra("word") as Word?
        this.creating = intent.getSerializableExtra("create") as Boolean
        if (word == null) word = Word()
        else this.difficulty = word.difficulty

        ConcreteWords.add(this)

        if (this.creating) {
            word_delete.visibility = View.GONE
        } else {
            word_delete.isActivated = true
            word_delete.setOnClickListener {
                if (it.isActivated) {
                    DataGetter.updateWord(word!!, "DELETE")
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
                word = updateFromFields(word!!)
                if (creating) {
                    DataGetter.updateWord(word!!, "POST")
                } else {
                    DataGetter.updateWord(word!!, "PUT")
                }

            } else {
                Toast.makeText(this, "You already clicked the button.", Toast.LENGTH_SHORT).show()
            }
            it.isActivated = false
        }
        word_abort.setOnClickListener { finish() }
        fillData(word!!)
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

    private fun updateFromFields(word: Word): Word {
        word.word = word_word.text.toString()
        word.category = word_category.text.toString()
        word.description = word_description.text.toString()
        word.hint1 = word_hint1.text.toString()
        word.hint2 = word_hint2.text.toString()
        word.hint3 = word_hint3.text.toString()
        word.difficulty = this.difficulty
        return word
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
                this.difficulty = i
            }
            word_difficulty.addView(button)
        }
    }
}
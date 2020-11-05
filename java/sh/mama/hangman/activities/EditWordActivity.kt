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
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_add_words.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.observer.ConcreteWords
import sh.mama.hangman.observer.IObserver
import sh.mama.hangman.R
import sh.mama.hangman.libs.DataGetter
import sh.mama.hangman.libs.DataGetter.getStuff
import sh.mama.hangman.libs.DataGetter.updateStuff
import sh.mama.hangman.libs.RequestType
import sh.mama.hangman.models.HighScore
import sh.mama.hangman.models.Word
import sh.mama.hangman.observer.ConcreteScores.setHighScores
import sh.mama.hangman.observer.ConcreteWords.setWords

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
                    GlobalScope.launch(Dispatchers.IO) {
                        val url = "https://mama.sh/hangman/api"
                        val type = object : TypeToken<List<Word>>() {}.type
                        updateStuff(word!!, url, RequestType.DELETE)
                        val data: MutableList<Word> = getStuff(url, type)
                        launch(Dispatchers.Main) {
                            setWords(data)
                        }
                    }
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
                GlobalScope.launch(Dispatchers.IO) {
                    val url = "https://mama.sh/hangman/api"
                    if (creating) {
                        updateStuff(word!!, url, RequestType.POST)
                    } else {
                        updateStuff(word!!, url, RequestType.PUT)
                    }
                    val type = object : TypeToken<List<Word>>() {}.type
                    val data: MutableList<Word> = getStuff(url, type)
                    launch(Dispatchers.Main) {
                        setWords(data)
                    }
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
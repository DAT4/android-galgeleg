package sh.mama.hangman

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.get
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_add_words.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.models.Category
import sh.mama.hangman.models.Word
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class AddWordsActivity : AppCompatActivity() {
    private var word = Word()
    private var creating = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_words)
        val data = intent.getSerializableExtra("word")
        this.creating = intent.getSerializableExtra("create") as Boolean
        if (data != null) {
            this.word = data as Word
        }
        if (this.creating){
            word_delete_box.removeAllViews()
        } else {
            word_delete.isActivated = true
            word_delete.setOnClickListener {
                if (it.isActivated) {
                    GlobalScope.launch(Dispatchers.IO){
                        updateWord("DELETE")
                        launch(Dispatchers.Main) {
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this, "You already clicked the button.", Toast.LENGTH_SHORT).show()
                }
                it.isActivated = false
            }
        }
        createButtons()
        word_add.isActivated = true
        word_add.setOnClickListener {
            if (it.isActivated) {
                updateFromFields()
                GlobalScope.launch(Dispatchers.IO){
                    if (creating)
                        updateWord("POST")
                    else
                        updateWord("PUT")
                    launch(Dispatchers.Main) {
                        finish()
                    }
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

    private fun updateFromFields(){
        this.word.word = word_word.text.toString()
        this.word.category = word_category.text.toString()
        this.word.description = word_description.text.toString()
        this.word.hint1 = word_hint1.text.toString()
        this.word.hint2 = word_hint2.text.toString()
        this.word.hint3 = word_hint3.text.toString()
    }

    private fun fillData(word: Word){
        word_word.setText(word.word)
        word_description.setText(word.description)
        word_hint1.setText(word.hint1)
        word_hint2.setText(word.hint2)
        word_hint3.setText(word.hint3)
        word_category.setText(word.category)
        val button = word_difficulty[word.difficulty-1] as Button
        button.setBackgroundColor(Color.GRAY)
    }

    private fun createButtons(){
        for(i in 1..10) {
            val button = Button(this)
            button.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = "$i"
            button.layoutParams.height = 100
            button.layoutParams.width = 100
            button.setTextColor(Color.GREEN)
            button.setTextSize(10F)
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


    private fun makeCategory() {
        GlobalScope.launch(Dispatchers.IO){
            val data = URL("https://mama.sh/hangman/api").readText()
        }
    }

    private fun parseShit(data: String): List<Category> {
        println(data)
        val gson = Gson()
        val categoriesType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(data, categoriesType)
    }

    private fun updateWord(action: String): String {
        val url = "https://mama.sh/hangman/api"
        val req = URL(url)
        val con = req.openConnection() as HttpURLConnection
        con.requestMethod = action
        con.connectTimeout = 300000
        con.doOutput = true
        val gson = Gson()
        val json = gson.toJson(this.word)
        println(json)
        val data = (json).toByteArray()
        con.setRequestProperty("User-Agent", "Your-Mom")
        con.setRequestProperty("Content-Type", "application/json")

        val request = DataOutputStream(con.outputStream)
        request.write(data)
        request.flush()
        con.inputStream.bufferedReader().use {
            val response = StringBuffer()
            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            return response.toString()
        }
    }
}
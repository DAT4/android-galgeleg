package sh.mama.hangman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_pick_context.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.models.Category
import java.lang.Exception
import java.net.URL

class PickContextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)
        getCategories()
    }

    private fun printButtons(data: List<Category>) {
        for (category in data) {
            println(category.title)
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = category.title
            button.setOnClickListener {
                val game = Intent(this, GameActivity::class.java)
                game.putExtra("word", category.getOne())
                startActivity(game)
            }
            categories.addView(button)
        }
    }

    private fun getCategories() {
        GlobalScope.launch {
            val data = URL("https://mama.sh/hangman/api").readText()
            launch(Dispatchers.Main) {
                try {
                    printButtons(parseShit(data))
                } catch (e: Exception) {
                    finish()
                }
            }
            return@launch
        }
    }

    private fun parseShit(data:String): List<Category>{
        println(data)
        val gson = Gson()
        val categoriesType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(data, categoriesType)
    }
}
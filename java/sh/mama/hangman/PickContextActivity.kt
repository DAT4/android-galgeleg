package sh.mama.hangman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pick_context.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sh.mama.hangman.models.Category
import java.net.URL

class PickContextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)

        getCSV()

    }

    private fun getCats(csv: ArrayList<List<String>>) {
        val contexts = getCategories(csv)

        for (category in contexts) {
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

    private fun getCategories(csv: ArrayList<List<String>>): ArrayList<Category> {
        val categories = ArrayList<Category>()
        csv[0].forEachIndexed { i, title ->
            val category = Category(title)

            csv.forEach{
                if (it[i] != "") {
                    category.add(it[i])
                }
            }
            categories.add(category)
        }

        return categories
    }

    private fun getCSV() {
        GlobalScope.launch(Dispatchers.IO) {
            val url =
                "https://docs.google.com/spreadsheets/d/1WwOCyZpTm13_J49AX4KICdvjTH2vxACD6C7tYmIKIhg/export?format=csv&id=1WwOCyZpTm13_J49AX4KICdvjTH2vxACD6C7tYmIKIhg"
            val csv = ArrayList<List<String>>()
            URL(url).readText().trim().split("\n").forEach { csv.add(it.trim().split(",")) }
            for (line in csv) {
                println(line.toString())
            }
            launch(Dispatchers.Main) {
                getCats(csv)
                Toast.makeText(this@PickContextActivity, "Data loaded...", Toast.LENGTH_SHORT)
                    .show()
            }

            return@launch
        }
    }
}
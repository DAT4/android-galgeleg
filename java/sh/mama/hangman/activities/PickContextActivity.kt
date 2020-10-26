package sh.mama.hangman.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_pick_context.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.R
import sh.mama.hangman.adapters.CategoryAdapter
import sh.mama.hangman.models.Category
import java.net.URL

class PickContextActivity : AppCompatActivity() {
    private var edit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_context)
        this.edit = intent.getSerializableExtra("edit") as Boolean
        getCategories()
    }

    private fun printButtons(data: List<Category>) {
        val adapter = CategoryAdapter(data, edit)
        categories.adapter = adapter
        categories.layoutManager = LinearLayoutManager(this)
    }

    private fun getCategories() {
        GlobalScope.launch(Dispatchers.IO){
            val data = URL("https://mama.sh/hangman/api").readText()
            launch(Dispatchers.Main) {
                try {
                    printButtons(parseShit(data))
                } catch (e: Exception) {
                    finish()
                }
            }
        }
    }

    private fun parseShit(data: String): MutableList<Category> {
        val gson = Gson()
        val categoriesType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(data, categoriesType)
    }
}
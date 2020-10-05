package sh.mama.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play.setOnClickListener {
            val game = Intent(this, PickContextActivity::class.java)
            game.putExtra("edit", false)
            startActivity(game)
        }
        create_categories.setOnClickListener {
            val create = Intent(this, PickContextActivity::class.java)
            create.putExtra("edit", true)
            startActivity(create)
        }
        add_word_button.setOnClickListener {
            val addWord = Intent(this, AddWordsActivity::class.java)
            startActivity(addWord)
        }
        exit.setOnClickListener {
            end()
        }
    }

    private fun end() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}


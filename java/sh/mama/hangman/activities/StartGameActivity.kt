package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_game.*
import sh.mama.hangman.R
import sh.mama.hangman.models.Word

class StartGameActivity : AppCompatActivity() {
    private val buttonClick = AlphaAnimation(1F, 0.1F)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        val word = intent.getSerializableExtra("word") as Word

        start_game.setOnClickListener {
            it.startAnimation(buttonClick)
            var name = ""
            name = player_name.text.toString()
            if (name == "")
                Toast.makeText(this, "You have to pick a name", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(this, PlayGameActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("word", word)
                startActivity(intent)
                finish()
            }
        }

    }
}


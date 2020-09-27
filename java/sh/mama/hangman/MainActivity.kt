package sh.mama.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play.setOnClickListener {
            val game = Intent(this, PickContextActivity::class.java)
            startActivity(game)
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


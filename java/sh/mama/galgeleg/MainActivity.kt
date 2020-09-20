package sh.mama.galgeleg

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.*
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import sh.mama.galgeleg.models.Word
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play.setOnClickListener {
            val game = Intent(this, GameActivity::class.java)
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


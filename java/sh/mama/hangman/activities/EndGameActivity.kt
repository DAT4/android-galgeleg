package sh.mama.hangman.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sh.mama.hangman.R
import kotlin.system.exitProcess

class EndGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun end() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}


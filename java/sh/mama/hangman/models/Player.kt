package sh.mama.hangman.models

import java.io.Serializable

data class Player(
    val name : String,
    var score : Int
) : Serializable {
    fun setScore(time :Int, difficulty:Int ) {
        this.score = difficulty*1000/time
    }
}


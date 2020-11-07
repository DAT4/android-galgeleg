package sh.mama.hangman.models

import sh.mama.hangman.R

class HangMan {
    var level = 0

    private val states = listOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h,
        R.drawable.i,
        R.drawable.j,
        R.drawable.loose,
    )

    fun kill(): Boolean {
        this.level++
        return this.states.size - 1 > this.level
    }

    fun getState(): Int {
        return this.states[this.level]
    }
}

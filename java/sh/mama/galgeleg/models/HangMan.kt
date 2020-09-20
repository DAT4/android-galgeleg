package sh.mama.galgeleg.models

import android.graphics.drawable.Drawable
import sh.mama.galgeleg.R

class HangMan() {
    private var level = 0

    private val states = listOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g
    )

    fun kill(): Boolean {
        this.level += 1
        return this.states.size-1 > this.level
    }

    fun getState(): Int {
        return this.states[this.level]
    }
}

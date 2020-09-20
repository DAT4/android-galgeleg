package sh.mama.galgeleg.models

class HangMan() {
    private var level = 0

    public fun kill(): Boolean {
        this.level += 1
        return 8 < this.level
    }
}

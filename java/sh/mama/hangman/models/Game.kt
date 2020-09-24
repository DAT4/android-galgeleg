package sh.mama.hangman.models

class Game(word: String) {
    val hangman = HangMan()
    val letters = word.toList().map { Letter(it) }
    val options = "ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ ".toList().map { Letter(it) }

    fun isDone(): Boolean {
        var isDone = true
        letters.forEach {
            if (!it.isGuessed()){
                isDone = false
            }
        }
        return isDone
    }

    fun guess(letter: Letter): Boolean {
        var guessed = false

        this.options.forEach {
            it.guess(letter)
        }

        this.letters.forEach {
            if (it.guess(letter)) {
                guessed = true
            }
        }

        return if (!guessed)
            this.hangman.kill()
        else
            guessed
    }
}

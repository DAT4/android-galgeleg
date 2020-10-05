package sh.mama.hangman.models

data class Game(val word: Word) {
    val hangman = HangMan()
    val options = "ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ ".toList().map { Letter(it) }

    fun isDone(): Boolean {
        var isDone = true
        this.word.letters.forEach {
            if (!it.isGuessed()) {
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

        this.word.letters.forEach {
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

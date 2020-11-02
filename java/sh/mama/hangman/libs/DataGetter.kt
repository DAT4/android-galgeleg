package sh.mama.hangman.libs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.Observer.ConcreteScores
import sh.mama.hangman.models.Word
import sh.mama.hangman.Observer.ConcreteWords
import sh.mama.hangman.models.HighScore
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DataGetter {

    fun getWords() {
        GlobalScope.launch(Dispatchers.IO) {
            val data = URL("https://mama.sh/hangman/api").readText()
            launch(Dispatchers.Main) {
                ConcreteWords.setWords(parseWords(data))
            }
        }
    }

    //Man kan vist bruge decocator her. eller passe passing algoritmen ind som parameter
    fun getHighScores() {
        GlobalScope.launch(Dispatchers.IO) {
            val data = URL("https://mama.sh/hangman/api/highscores").readText()
            launch(Dispatchers.Main) {
                ConcreteScores.setHighScores(parseScores(data))
            }
        }
    }

    private fun parseScores(data: String): MutableList<HighScore> {
        val gson = Gson()
        val wordsType = object : TypeToken<List<HighScore>>() {}.type
        return gson.fromJson(data, wordsType)
    }

    private fun parseWords(data: String): MutableList<Word> {
        val gson = Gson()
        val wordsType = object : TypeToken<List<Word>>() {}.type
        return gson.fromJson(data, wordsType)
    }

    fun updateWord(word: Word, action: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = "https://mama.sh/hangman/api"
            val req = URL(url)
            val con = req.openConnection() as HttpURLConnection
            con.requestMethod = action
            con.connectTimeout = 300000
            con.doOutput = true
            val gson = Gson()
            val json = gson.toJson(word)
            println(json)
            val data = (json).toByteArray()
            con.setRequestProperty("Content-Type", "application/json")

            val request = DataOutputStream(con.outputStream)
            request.write(data)
            request.flush()
            con.inputStream.bufferedReader().use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                launch(Dispatchers.Main) {
                    getWords()
                }
            }
        }
    }

    fun updateScore(score: HighScore, action: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = "https://mama.sh/hangman/api/highscores"
            val req = URL(url)
            val con = req.openConnection() as HttpURLConnection
            con.requestMethod = action
            con.connectTimeout = 300000
            con.doOutput = true
            val gson = Gson()
            val json = gson.toJson(score)
            println(json)
            val data = (json).toByteArray()
            con.setRequestProperty("Content-Type", "application/json")

            val request = DataOutputStream(con.outputStream)
            request.write(data)
            request.flush()
            con.inputStream.bufferedReader().use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                launch(Dispatchers.Main) {
                    getHighScores()
                }
            }
        }
    }
}
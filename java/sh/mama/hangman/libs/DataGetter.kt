package sh.mama.hangman.libs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.models.Word
import sh.mama.hangman.Observer.wordsHolder
import sh.mama.hangman.Observer.wordsHolder.addWord
import sh.mama.hangman.Observer.wordsHolder.deleteWord
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DataGetter {

    fun getCategories() {
        GlobalScope.launch(Dispatchers.IO) {
            val data = URL("https://mama.sh/hangman/api").readText()
            launch(Dispatchers.Main) {
                wordsHolder.setWords(parseShit(data))
            }
        }
    }

    private fun parseShit(data: String): MutableList<Word> {
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
            con.setRequestProperty("User-Agent", "Your-Mom")
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
                    if (action == "DELETE")
                        deleteWord(word)
                    else
                        addWord(word)
                }
            }
        }
    }
}
package sh.mama.hangman.libs

import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.DataOutputStream
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

object DataGetter {

    fun <T> getStuff(url: String, type: Type): T {
        val data = URL(url).readText()
        return Gson().fromJson(data, type)
    }

    fun updateStuff(model: Any, url: String, action: RequestType){
        val req = URL(url)
        val con = req.openConnection() as HttpURLConnection
        con.requestMethod = action.toString()
        con.connectTimeout = 300000
        con.doOutput = true
        val json = Gson().toJson(model)
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
        }
    }
}
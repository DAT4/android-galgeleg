package sh.mama.hangman.observer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sh.mama.hangman.enumerators.RequestType
import sh.mama.hangman.libs.DataGetter.getStuff
import sh.mama.hangman.libs.DataGetter.updateStuff
import java.lang.reflect.Type

interface ICache: IObservable{
    val type: Type
    var content: ArrayList<*>
    val url: String

    fun cache(){
        GlobalScope.launch(Dispatchers.IO){
            val data:ArrayList<*> = getStuff(this@ICache.url,this@ICache.type)
            launch(Dispatchers.Main){
                this@ICache.content = data
                sendUpdateEvent()
            }
        }
    }
    fun update(model:Any, method: RequestType){
        GlobalScope.launch(Dispatchers.IO){
            updateStuff(model,this@ICache.url,method)
        }
        cache()
    }
}
package sh.mama.hangman.observer

import java.util.*
import kotlin.collections.ArrayList

interface IObservable {
    val observers: ArrayList<IObserver>
    fun add(observer: IObserver){
        observers.add(observer)
    }
    fun remove(observer: IObserver){
        observers.remove(observer)
    }
    fun sendUpdateEvent(){
        observers.forEach{it.update()}
    }
}
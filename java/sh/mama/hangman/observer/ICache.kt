package sh.mama.hangman.observer

import java.lang.reflect.Type

interface ICache{
    val type: Type
    var content: ArrayList<T>


}
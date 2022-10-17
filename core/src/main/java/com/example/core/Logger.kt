package com.example.core

class Logger(private val tag :String,private val isDebug:Boolean = true) {
    fun log(msg: String){
        if (isDebug){
            printLogD(tag,msg)
            //production logging-Crashlytics or w/
        }
    }

    companion object Factory{
        fun buildDebug(tag:String): Logger{
            return Logger(tag,true)
        }
        fun buildRelease(tag:String): Logger{
            return Logger(tag,false)
        }
    }

}
private fun printLogD(tag: String, msg: String) {
    println("$tag: $msg")
}
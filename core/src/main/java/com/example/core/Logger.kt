package com.example.core

class Logger(private val tag :String,private val isDebug:Boolean = true) {
    fun log(msg: String){
        if (!isDebug){
            //production logging-Crashlytics or w/
        }else{
            printLogD(tag,msg)
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
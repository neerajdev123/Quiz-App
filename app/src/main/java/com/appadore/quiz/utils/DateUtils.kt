package com.appadore.quiz.utils

import java.util.Calendar

object DateUtils {

    fun getChallengeTime(hour : Int, min : Int) : Long{
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.clear(Calendar.SECOND)
        val timeLong = calendar.timeInMillis
        val currentTime = System.currentTimeMillis()
        return timeLong - currentTime
    }

    fun getChallengeDisplayTime(millisUntilFinished : Long) : String{
        val secondsValue = millisUntilFinished / 1000
        val hours = secondsValue / 3600
        val minutes = (secondsValue % 3600) / 60
        val seconds = secondsValue % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
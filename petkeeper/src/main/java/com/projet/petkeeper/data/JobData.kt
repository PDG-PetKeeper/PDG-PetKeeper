package com.projet.petkeeper.data

import android.net.Uri
import java.util.Calendar
import java.util.GregorianCalendar

data class JobData (
    val id: Long,
    val poster: String?,  //change to data class for user profiles
    val worker: String?, //change to data class for user profiles
    val image: Uri?,
    var title: String,
    var pet: String,
    var description: String,
    var startDate: GregorianCalendar,
    var endDate: GregorianCalendar,
    var hourlyPay: String,
    var location: String = "unknown"
){
    fun getDateString(start: Boolean): String{
        if (start){
            return startDate.get(Calendar.YEAR).toString() +
                    "-" + startDate.get(Calendar.MONTH).toString() +
                    "-" + startDate.get(Calendar.DATE).toString()
        } else {
            return endDate.get(Calendar.YEAR).toString() +
                    "-" + endDate.get(Calendar.MONTH).toString() +
                    "-" + endDate.get(Calendar.DATE).toString()
        }
    }
}

/*
object JobDataExample {
    val jobDataExampleList = listOf(
        JobData(
            1L,
            1,
            null,
            R.drawable.cat_1,
            "test 1",
            PetType.cat,
            "Description 1",
            GregorianCalendar(2023,9,12),
            GregorianCalendar(2023,9,15),
            "2.55"
        ),

        JobData(
            2L,
            2,
            null,
            R.drawable.cat_1,
            "test 2",
            PetType.cat,
            "Description 2",
            GregorianCalendar(2023,8,12),
            GregorianCalendar(2023,9,15),
            "7"
        ),

        JobData(
            3L,
            2,
            null,
            R.drawable.cat_1,
            "test 3",
            PetType.cat,
            "Description 3 ojdfhowefhoewf efjhwefckb wekfbgwoeib weifugbwuebc wekfhcbwkbc",
            GregorianCalendar(2023,9,21),
            GregorianCalendar(2023,9,27),
            "12"
        ),

        JobData(
            4L,
            3,
            null,
            R.drawable.cat_1,
            "test 3",
            PetType.cat,
            "Description 3",
            GregorianCalendar(2023,9,11),
            GregorianCalendar(2023,9,13),
            "6"
        ),
        JobData(
            5L,
            1,
            null,
            R.drawable.cat_1,
            "test 1",
            PetType.cat,
            "Description 1",
            GregorianCalendar(2023,9,12),
            GregorianCalendar(2023,9,15),
            "2.55"
        ),

        JobData(
            6L,
            2,
            null,
            R.drawable.cat_1,
            "test 2",
            PetType.cat,
            "Description 2",
            GregorianCalendar(2023,8,12),
            GregorianCalendar(2023,9,15),
            "7"
        ),

        JobData(
            7L,
            2,
            null,
            R.drawable.cat_1,
            "test 3",
            PetType.cat,
            "Description 3 ojdfhowefhoewf efjhwefckb wekfbgwoeib weifugbwuebc wekfhcbwkbc",
            GregorianCalendar(2023,9,21),
            GregorianCalendar(2023,9,27),
            "12"
        ),

        JobData(
            8L,
            3,
            null,
            R.drawable.cat_1,
            "test 3",
            PetType.cat,
            "Description 3",
            GregorianCalendar(2023,9,11),
            GregorianCalendar(2023,9,13),
            "6"
        ),
        JobData(
            9L,
            1,
            null,
            R.drawable.cat_1,
            "test 1",
            PetType.cat,
            "Description 1",
            GregorianCalendar(2023,9,12),
            GregorianCalendar(2023,9,15),
            "2.55"
        ),

        JobData(
            10L,
            2,
            null,
            R.drawable.cat_1,
            "test 2",
            PetType.cat,
            "Description 2",
            GregorianCalendar(2023,8,12),
            GregorianCalendar(2023,9,15),
            "7"
        ),

        JobData(
            11L,
            2,
            null,
            R.drawable.cat_1,
            "test 3",
            PetType.cat,
            "Description 3 ojdfhowefhoewf efjhwefckb wekfbgwoeib weifugbwuebc wekfhcbwkbc",
            GregorianCalendar(2023,9,21),
            GregorianCalendar(2023,9,27),
            "12"
        ),

        JobData(
            12L,
            3,
            null,
            R.drawable.cat_1,
            "test 3",
            PetType.cat,
            "Description 3",
            GregorianCalendar(2023,9,11),
            GregorianCalendar(2023,9,13),
            "6"
        ),
    )
}*/
package com.projet.petkeeper.data

import android.net.Uri
import java.util.Calendar
import java.util.GregorianCalendar

data class JobData (
    val id: Long?,
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
    fun getDateString(fromStartDate: Boolean): String{
        if (fromStartDate){
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


object JobDataExample {
    val jobDataExampleList = listOf(
        JobData(
            1L,
            "poster 1",
            null,
            null,
            "test 1",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this",
            GregorianCalendar(2023,9,12),
            GregorianCalendar(2023,9,15),
            "2.55"
        ),

        JobData(
            2L,
            "poster 2",
            null,
            null,
            "test 2",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,15),
            GregorianCalendar(2023,9,16),
            "2.55"
        ),

        JobData(
            3L,
            "poster 2",
            null,
            null,
            "test 3",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,14),
            GregorianCalendar(2023,9,17),
            "2.55"
        ),

        JobData(
            4L,
            "poster 1",
            null,
            null,
            "test 4",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,5),
            GregorianCalendar(2023,9,7),
            "2.55"
        ),
        JobData(
            5L,
            "poster 1",
            null,
            null,
            "test 5",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,6),
            GregorianCalendar(2023,9,16),
            "2.55"
        ),

        JobData(
            6L,
            "poster 1",
            null,
            null,
            "test 6",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,12),
            GregorianCalendar(2023,9,17),
            "2.55"
        ),

        JobData(
            7L,
            "poster 3",
            null,
            null,
            "test 7",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,12,25),
            GregorianCalendar(2023,12,25),
            "2.55"
        ),

        JobData(
            8L,
            "poster 4",
            null,
            null,
            "test 8",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,10),
            GregorianCalendar(2023,9,11),
            "2.55"
        ),
        JobData(
            9L,
            "poster 1",
            null,
            null,
            "test 9",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,7),
            GregorianCalendar(2023,9,14),
            "2.55"
        ),

        JobData(
            10L,
            "poster 5",
            null,
            null,
            "test 10",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,12),
            GregorianCalendar(2023,9,13),
            "2.55"
        ),

        JobData(
            11L,
            "poster 5",
            null,
            null,
            "test 11",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,9,21),
            GregorianCalendar(2023,9,25),
            "2.55"
        ),

        JobData(
            12L,
            "poster 3",
            null,
            null,
            "test 12",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            GregorianCalendar(2023,10,30),
            GregorianCalendar(2023,11,5),
            "2.55"
        ),
    )
}
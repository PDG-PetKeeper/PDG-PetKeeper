package com.projet.petkeeper.data

import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

/**
 * Data class representing job data.
 *
 * @property id Unique identifier for the job.
 * @property poster The person or entity posting the job.
 * @property worker The worker assigned to the job.
 * @property image Image associated with the job.
 * @property title Title of the job.
 * @property pet Pet type associated with the job.
 * @property description Description of the job.
 * @property startDate The starting date and time of the job.
 * @property endDate The ending date and time of the job.
 * @property pay The payment amount for the job.
 * @property location The location of the job.
 */
data class JobData (
    val id: String? = null,
    val poster: String? = null,
    var worker: String? = null,
    var image: String? = null,
    var title: String? = null,
    var pet: String? = null,
    var description: String? = null,
    var startDate: Timestamp? = null,
    var endDate: Timestamp? = null,
    var pay: String? = null,
    var location: String? = null,
){
    /**
     * Get a formatted date string based on the specified date (start date or end date).
     *
     * @param fromStartDate If true, the date string will be generated from the start date; otherwise, from the end date.
     * @return A formatted date string in the "YYYY-MM-DD" format.
     */
    fun getDateString(fromStartDate: Boolean): String{
        val temp = GregorianCalendar()
        if (fromStartDate){
            temp.time = startDate?.toDate() ?: Date(0L)
            return temp.get(Calendar.YEAR).toString() +
                    "-" + temp.get(Calendar.MONTH).toString() +
                    "-" + temp.get(Calendar.DATE).toString()
        } else {
            temp.time = endDate?.toDate() ?: Date(0L)
            return temp.get(Calendar.YEAR).toString() +
                    "-" + temp.get(Calendar.MONTH).toString() +
                    "-" + temp.get(Calendar.DATE).toString()
        }
    }
}

/**
 * Object containing a list of example JobData instances for testing or demonstration purposes.
 */
object JobDataExample {
    val jobDataExampleList = listOf(
        JobData(
            "1L",
            "poster 1",
            null,
            null,
            "test 1",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this",
            Timestamp(GregorianCalendar(2023,9,12).time),
            Timestamp(GregorianCalendar(2023,9,15).time),
            "2.55"
        ),

        JobData(
            "2L",
            "poster 2",
            null,
            null,
            "test 2",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,15).time),
            Timestamp(GregorianCalendar(2023,9,16).time),
            "2.55"
        ),

        JobData(
            "3L",
            "poster 2",
            null,
            null,
            "test 3",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,14).time),
            Timestamp(GregorianCalendar(2023,9,17).time),
            "2.55"
        ),

        JobData(
            "4L",
            "poster 1",
            null,
            null,
            "test 4",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,5).time),
            Timestamp(GregorianCalendar(2023,9,7).time),
            "2.55"
        ),
        JobData(
            "5L",
            "poster 1",
            null,
            null,
            "test 5",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,6).time),
            Timestamp(GregorianCalendar(2023,9,16).time),
            "2.55"
        ),

        JobData(
            "6L",
            "poster 1",
            null,
            null,
            "test 6",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,12).time),
            Timestamp(GregorianCalendar(2023,9,17).time),
            "2.55"
        ),

        JobData(
            "7L",
            "poster 3",
            null,
            null,
            "test 7",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp( GregorianCalendar(2023,12,25).time),
            Timestamp(GregorianCalendar(2023,12,25).time),
            "2.55"
        ),

        JobData(
            "8L",
            "poster 4",
            null,
            null,
            "test 8",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,10).time),
            Timestamp(GregorianCalendar(2023,9,11).time),
            "2.55"
        ),
        JobData(
            "9L",
            "poster 1",
            null,
            null,
            "test 9",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,7).time),
            Timestamp(GregorianCalendar(2023,9,14).time),
            "2.55"
        ),

        JobData(
            "10L",
            "poster 5",
            null,
            null,
            "test 10",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,12).time),
            Timestamp(GregorianCalendar(2023,9,13).time),
            "2.55"
        ),

        JobData(
            "11L",
            "poster 5",
            null,
            null,
            "test 11",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,9,21).time),
            Timestamp(GregorianCalendar(2023,9,25).time),
            "2.55"
        ),

        JobData(
            "12L",
            "poster 3",
            null,
            null,
            "test 12",
            "cat",
            "This is a description, it is long and therefore heavy. but we store it like this 1",
            Timestamp(GregorianCalendar(2023,10,30).time),
            Timestamp(GregorianCalendar(2023,11,5).time),
            "2.55"
        ),
    )
}
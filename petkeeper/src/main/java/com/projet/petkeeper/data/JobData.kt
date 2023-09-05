package com.projet.petkeeper.data

import androidx.annotation.DrawableRes
import com.projet.petkeeper.R
import java.util.GregorianCalendar

data class JobData (
    val id: Long,
    val poster: Int,  //change to data class for user profiles
    val worker: Int?, //change to data class for user profiles
    @DrawableRes val images: List<Int>,
    var title: String,
    var pet: PetType,
    var description: String,
    var startDateTime: GregorianCalendar,
    var endDateTime: GregorianCalendar,
    var hourlyPay: String,
)

object JobDataExample {
    val jobDataExampleList = listOf(
        JobData(
            1L,
            1,
            null,
            listOf(R.drawable.cat),
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
            listOf(R.drawable.cat),
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
            listOf(R.drawable.cat),
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
            listOf(R.drawable.cat),
            "test 3",
            PetType.cat,
            "Description 3",
            GregorianCalendar(2023,9,11),
            GregorianCalendar(2023,9,13),
            "6"
        )
    )
}
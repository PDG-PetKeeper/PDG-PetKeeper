package com.projet.petkeeper.chat

import com.projet.petkeeper.R

data class UserProfile(
    val name: String,
    val profileImage: Int // Resource ID for the profile image (e.g., R.drawable.profile_image)
)
object UserProfileData {
    val userProfileList = listOf(
        UserProfile("User1", R.drawable.cat),
        UserProfile("User2", R.drawable.cat),
        UserProfile("User3", R.drawable.cat),
        UserProfile("User4", R.drawable.cat),
        UserProfile("User5", R.drawable.cat),
    )
    //  get the properties of UserProfile at a given index
    fun getUserProfileByName(name: String): UserProfile? {
        return userProfileList.find { it.name == name }
    }
}


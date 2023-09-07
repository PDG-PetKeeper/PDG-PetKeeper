package com.projet.petkeeper.ui

import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.MessageData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.UserPair
import com.projet.petkeeper.navigation.NavBarItem

data class PetKeeperUIState(
    val mustShowNavBar: Boolean = true,
    val navBarItemList: List<NavBarItem> = NavBarItem.getNavBarItemList(),
    val currentNavBarItemIndex: Int = 2,

    val currentJobList: List<JobData> = emptyList(),
    val currentSelectedJob: JobData? = null,

    val userPairList: List<UserPair> = emptyList(),
    val currentUserPair: UserPair? = null,
    val currentChatter: UserData? = null,
    val currentMessageList: List<MessageData> = emptyList()

    // change to profile data class list of profiles we have started messaging

)


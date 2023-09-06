package com.projet.petkeeper.ui

import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.navigation.NavBarItem


data class PetKeeperUIState(
    val mustShowNavBar: Boolean = true,
    val navBarItemList: List<NavBarItem> = NavBarItem.getNavBarItemList(),
    val currentNavBarItemIndex: Int = 2,

    val currentJobList: List<JobData> = emptyList(),
    val currentSelectedJob: JobData? = null,

    // change to profile data class list of profiles we have started messaging
    val chatList: List<Int> = emptyList(),
    val currentChat: Int? = null    // change to profile data class later
)
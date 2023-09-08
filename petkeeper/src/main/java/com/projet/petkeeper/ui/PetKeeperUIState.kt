package com.projet.petkeeper.ui

import com.projet.petkeeper.data.JobData
import com.projet.petkeeper.data.MessageData
import com.projet.petkeeper.data.UserData
import com.projet.petkeeper.data.UserPair
import com.projet.petkeeper.navigation.NavBarItem

/**
 * Represents the current state of the PetKeeper UI, including various data related to navigation,
 * user interactions, and content display.
 *
 * @param mustShowNavBar Indicates whether the navigation bar must be shown on the screen.
 * @param navBarItemList The list of navigation bar items to be displayed.
 * @param currentNavBarItemIndex The index of the currently selected navigation bar item.
 * @param currentJobList The list of job data currently being displayed.
 * @param currentSelectedJob The selected job data, if any.
 * @param userPairList The list of user pairs, possibly for chat or connections.
 * @param currentUserPair The currently selected user pair.
 * @param currentChatter The user associated with the current chat conversation.
 * @param currentMessageList The list of messages in the current chat conversation.
 */
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


)


package com.projet.petkeeper.data

data class UserPair(
    val id: String? = null,
    val userId1: String? = null,
    val userId2: String? = null,
) {
    //val userData1 = fetchUserDataFromId(userId1)

}

//fun fetchUserDataFromId(userId: String) : UserData{
//    val db = Firebase.firestore
//
//    CoroutineScope(Dispatchers.IO).launch {
//
//    }
//}
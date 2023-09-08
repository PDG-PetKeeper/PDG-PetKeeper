package com.projet.petkeeper

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirestoreConnectionInstrumentedTest {

    private lateinit var firestore: FirebaseFirestore
    private val collectionRefPath = "test_data"
    private val TAG = "FirestoreTest"

    @Before
    fun setUp() {
        firestore = FirebaseFirestore.getInstance()
        firestore.useEmulator("127.0.0.1", 8080) // default emulator host and port
    }

    @Test
    fun testAddAndReadDocument() {
        val data = hashMapOf(
            "name" to "Bob",
            "age" to 25
        )

        val collectionRef = firestore.collection(collectionRefPath)

        collectionRef.add(data)
            .addOnSuccessListener { documentReference ->
                val docId = documentReference.id // Get the auto-generated document ID
                Log.d(TAG, "Document added with ID: $docId")

                // Read function with the obtained document ID
                testReadDocument(docId)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }

    private fun testReadDocument(docId: String) {
        val docRef = firestore.collection(collectionRefPath).document(docId)

        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val name = documentSnapshot.getString("name")
                    val age = documentSnapshot.getLong("age")

                    Log.d(TAG, "Name: $name, Age: $age")
                } else {
                    Log.d(TAG, "Document does not exist")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting document", e)
            }
    }
}

package com.pop.moviemanagement.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.pop.moviemanagement.model.theater.Theater
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreManager(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()

    private val auth = AuthManager(context)
    val userId = auth.getCurrentUser()?.uid

    fun getTheatersFlow(): Flow<List<Theater>> = callbackFlow {

        //val theaterRef = firestore.collection("theaters").whereEqualTo("userId", userId).orderBy("name")
        val theaterRef = firestore.collection("theaters").orderBy("name")

        val subscription = theaterRef.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val theaters = mutableListOf<Theater>()
                for (document in querySnapshot.documents){
                    val theater = document.toObject(Theater::class.java)

                    theater?.id = document.id
                    theater?.let { theaters.add(it) }
                }
                trySend(theaters).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }

}
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
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()

    private val auth = AuthManager(context)
    val userId = auth.getCurrentUser()?.uid

    fun getTheatersFlow(): Flow<List<Theater>> = callbackFlow {

        //val theaterRef = firestore.collection("theaters").whereEqualTo("userId", userId).orderBy("name")
        if (userId == null) {
            close(Exception("User ID is null"))
        } else {

            val theaterRef = firestore.collection("theaters").orderBy("name")
            val favoriteRef = firestore.collection("users").document(userId).collection("favorites")
            val theaters = mutableListOf<Theater>()

            val subscription = theaterRef.addSnapshotListener { snapshot, _ ->
                snapshot?.let { querySnapshot ->

                    for (document in querySnapshot.documents) {
                        val theater = document.toObject(Theater::class.java)

                        theater?.id = document.id
                        theater?.let { theaters.add(it) }
                    }
                    trySend(theaters).isSuccess
                }
                favoriteRef.get().addOnCompleteListener { favorites ->
                    if (favorites.isSuccessful) {
                        val favoriteIds = favorites.result?.documents?.map { it.id } ?: emptyList()
                        theaters.forEach { it.favorite = favoriteIds.contains(it.id) }
                        trySend(theaters).isSuccess
                    } else {
                        Log.e(TAG, "Error getting favorites", favorites.exception)
                    }
                }
            }
            awaitClose { subscription.remove() }
        }
    }

    /*suspend fun updateTheater(theater: Theater){
        val theaterRef = theater.id?.let { firestore.collection("theaters").document(it) }
        theaterRef?.update("favorite", theater.favorite)
    }*/

    suspend fun setFavorite(theaterId: String, isFavorite: Boolean) {
        if (userId == null) {
            Log.e("FirestoreManager", "User ID is null. Make sure the user is authenticated.")
            return
        }

        val userRef = firestore.collection("users").document(userId)
        val favoriteRef = userRef.collection("favorites").document(theaterId)

        try {
            // Verificar si el documento de usuario existe
            val userDoc = userRef.get().await()
            if (!userDoc.exists()) {
                // Crear el documento de usuario si no existe
                userRef.set(mapOf("createdAt" to System.currentTimeMillis())).await()
            }

            if (isFavorite) {
                favoriteRef.set(mapOf("favorite" to true)).await()
                Log.d("FirestoreManager", "Theater $theaterId added to favorites")
            } else {
                favoriteRef.delete().await()
                Log.d("FirestoreManager", "Theater $theaterId removed from favorites")
            }
        } catch (e: Exception) {
            Log.e("FirestoreManager", "Error updating favorite status", e)
        }
    }

}

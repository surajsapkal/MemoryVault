package com.example.memoryvault.data.remote

import com.example.memoryvault.data.remote.model.FirestoreMemory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun userMemoriesCollection() =
        auth.currentUser?.uid?.let { uid ->
            firestore
                .collection("users")
                .document(uid)
                .collection("memories")
        }
            ?: throw IllegalStateException(
                "Cannot access Firestore without authenticated user"
            )

    suspend fun upsertMemories(
        memories: List<FirestoreMemory>
    ) {
        if (memories.isEmpty()) return

        val collection = userMemoriesCollection()

        /*Firestore batched writes have limits,
        so don't assume an unlimited number of writes in one batch.
        Keeping our own chunk below the documented limit gives us,
        room for future rule/access overhead.*/

        memories.chunked(400).forEach { chunk ->

            val batch = firestore.batch()

            chunk.forEach { memory ->

                val document = collection.document(memory.id)

                batch.set(document, memory)
            }

            batch.commit().await()
        }
    }

    suspend fun deleteMemories(
        memoryIds: List<String>
    ) {
        if (memoryIds.isEmpty()) return

        val collection = userMemoriesCollection()

        memoryIds.chunked(400).forEach { chunk ->

            val batch = firestore.batch()

            chunk.forEach { id ->
                batch.delete(collection.document(id))
            }

            batch.commit().await()
        }
    }

    suspend fun getAllMemories(): List<FirestoreMemory> {

        val snapshot = userMemoriesCollection()
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->

            try {
                document.toObject(FirestoreMemory::class.java)
                    ?.copy(id = document.id)
            } catch (_: Exception) {
                null
            }
        }
    }
}
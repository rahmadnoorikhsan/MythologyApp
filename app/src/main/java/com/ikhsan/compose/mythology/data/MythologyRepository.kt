package com.ikhsan.compose.mythology.data

import com.ikhsan.compose.mythology.model.Mythology
import kotlinx.coroutines.flow.Flow

interface MythologyRepository {
    fun searchMythology(query: String): Flow<List<Mythology>>

    fun getMythologyById(id: Int): Flow<Mythology>

    fun getFavoriteMythology(): Flow<List<Mythology>>

    fun updateMythology(id: Int, newState: Boolean): Flow<Boolean>
}
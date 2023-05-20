package com.ikhsan.compose.mythology.data

import com.ikhsan.compose.mythology.model.MythologiesData
import com.ikhsan.compose.mythology.model.Mythology
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MythologyRepositoryImpl @Inject constructor() : MythologyRepository {

    private val mythology = mutableListOf<Mythology>()

    init {
        if (mythology.isEmpty()) {
            mythology.addAll(MythologiesData.dummyMythologies)
        }
    }

    override fun getMythology() = flow {
        emit(mythology)
    }

    override fun searchMythology(query: String) = flow {
        val data = mythology.filter {
            it.title.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    override fun getMythologyById(id: Int): Flow<Mythology> {
        return flowOf(mythology.first { it.id == id })
    }

    override fun getFavoriteMythology(): Flow<List<Mythology>> {
        return flowOf(mythology.filter { it.favorite })
    }

    override fun updateMythology(id: Int, newState: Boolean): Flow<Boolean> {
        val index = mythology.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val mythologyIndex = mythology[index]
            mythology[index] = mythologyIndex.copy(favorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }
}
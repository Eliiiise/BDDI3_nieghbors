package com.example.neighbors.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.neighbors.dal.NeighborApiService
import com.example.neighbors.dal.memory.DummyNeighborApiService
import com.example.neighbors.dal.room.RoomNeighborDataSource
import com.example.neighbors.models.Neighbor

class NeighborRepository private constructor(application: Application) {
    private var apiService: NeighborApiService
    private val application = application

    init {
        apiService = RoomNeighborDataSource(application)
    }

    fun dataSourceInMemory(inMemory: Boolean) {
        if (inMemory) {
            apiService = RoomNeighborDataSource(application)
        } else {
            apiService = DummyNeighborApiService()
        }
    }

    fun getNeighbours(): LiveData<List<Neighbor>> = apiService.neighbours

    fun deleteNeighbor(neighbor: Neighbor) {
        apiService.deleteNeighbour(neighbor)
    }

    fun onFavorite(neighbor: Neighbor) {
        apiService.updateFavoriteStatus(neighbor)
    }

    fun addNeighbor(neighbor: Neighbor) {
        apiService.createNeighbour(neighbor)
    }

    companion object {
        private var instance: NeighborRepository? = null
        fun getInstance(application: Application): NeighborRepository {
            if (instance == null) {
                instance = NeighborRepository(application)
            }
            return instance!!
        }
    }
}

package com.example.neighbors.data

import androidx.lifecycle.LiveData
import com.example.neighbors.data.service.DummyNeighborApiService
import com.example.neighbors.data.service.NeighborApiService
import com.example.neighbors.models.Neighbor

class NeighborRepository {
    private val apiService: NeighborApiService

    init {
        apiService = DummyNeighborApiService()
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
        fun getInstance(): NeighborRepository {
            if (instance == null) {
                instance = NeighborRepository()
            }
            return instance!!
        }
    }
}

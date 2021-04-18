package com.example.neighbors.dal.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.neighbors.dal.NeighborApiService
import com.example.neighbors.dal.room.daos.NeighborDao
import com.example.neighbors.dal.utilis.toEntity
import com.example.neighbors.dal.utilis.toNeighbor
import com.example.neighbors.di.DI
import com.example.neighbors.models.Neighbor

class RoomNeighborDataSource(application: Application) : NeighborApiService {
    private val database: NeighborDataBase = NeighborDataBase.getDataBase(application)
    private val dao: NeighborDao = database.neighborDao()

    private val _neighors = MediatorLiveData<List<Neighbor>>()

    init {
        _neighors.addSource(dao.getNeighbors()) { entities ->
            _neighors.value = entities.map { entity ->
                entity.toNeighbor()
            }
        }
    }

    override val neighbours: LiveData<List<Neighbor>>
        get() = _neighors

    override fun deleteNeighbour(neighbor: Neighbor) {
        dao.deleteNeighbor(neighbor.toEntity())
    }

    override fun createNeighbour(neighbor: Neighbor) {
        dao.add(neighbor.toEntity())
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        dao.updateNeighborFavoriteStatus(neighbor.toEntity(!neighbor.favorite))
    }

    override fun updateDataNeighbour(neighbor: Neighbor) {
        TODO("Not yet implemented")
    }
}
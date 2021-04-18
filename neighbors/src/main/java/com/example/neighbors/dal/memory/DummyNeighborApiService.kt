package com.example.neighbors.dal.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbors.models.Neighbor
import com.example.neighbors.dal.NeighborApiService

class DummyNeighborApiService : NeighborApiService {

    private val _neighbours = MutableLiveData<List<Neighbor>>()

    override val neighbours: LiveData<List<Neighbor>>
        get() = _neighbours

    // s'éxecute à la création de ma classe
    init {
        _neighbours.postValue(DUMMY_NeighborS)
    }

    override fun deleteNeighbour(neighbor: Neighbor) {
        DUMMY_NeighborS.remove(neighbor)
        _neighbours.postValue(DUMMY_NeighborS)
    }

    override fun createNeighbour(neighbor: Neighbor) {
        neighbor.id = DUMMY_NeighborS.size.toLong() + 1
        _neighbours.postValue(DUMMY_NeighborS)
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        _neighbours.postValue(DUMMY_NeighborS)

        DUMMY_NeighborS.find {
            it.id == neighbor.id
        }?.let {
            it.favorite = !it.favorite
        }
    }

    override fun updateDataNeighbour(neighbor: Neighbor) {
        TODO("Not yet implemented")
    }
}
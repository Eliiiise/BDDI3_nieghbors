package com.example.neighbors.data.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbors.models.Neighbor

class DummyNeighborApiService : NeighborApiService {

    private val _neighbours = MutableLiveData<List<Neighbor>>()

    override val neighbours: LiveData<List<Neighbor>>
        get() = _neighbours

    // s'éxecute à la création de ma classe
    init {
        _neighbours.value = DUMMY_NeighborS.toList()
    }

    override fun deleteNeighbour(neighbor: Neighbor) {
        DUMMY_NeighborS.remove(neighbor)
        _neighbours.value = DUMMY_NeighborS.toList()
    }

    override fun createNeighbour(neighbor: Neighbor) {
        neighbor.id = DUMMY_NeighborS.size.toLong() + 1
        DUMMY_NeighborS.add(neighbor)
        _neighbours.value = DUMMY_NeighborS.toList()
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        neighbor.favorite = !neighbor.favorite
        _neighbours.value = DUMMY_NeighborS.toList()

        /*DUMMY_NeighborS.find {
            it.id == neighbor.id
        }?.let {
            it.favorite = !it.favorite
        }*/
    }

    override fun updateDataNeighbour(neighbor: Neighbor) {
        TODO("Not yet implemented")
    }
}
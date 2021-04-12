package com.example.neighbors.adapters

import com.example.neighbors.models.Neighbor

interface ListNeighborHandler {
    fun onDeleteNeibor(neighbor: Neighbor)
    fun onAddFavorite(neighbor: Neighbor)
    fun goWebsite(neighbor: Neighbor)
    fun onAddNeighbor()
}
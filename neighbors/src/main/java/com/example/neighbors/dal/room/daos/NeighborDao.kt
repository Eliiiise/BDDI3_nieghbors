package com.example.neighbors.dal.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.neighbors.dal.room.entities.NeighborEntity

@Dao
interface NeighborDao {
    @Query("SELECT * from neighbors")
    fun getNeighbors(): LiveData<List<NeighborEntity>>

    @Insert
    fun add(vararg neighborEntity: NeighborEntity)

    @Delete
    fun deleteNeighbor(entity: NeighborEntity)
}
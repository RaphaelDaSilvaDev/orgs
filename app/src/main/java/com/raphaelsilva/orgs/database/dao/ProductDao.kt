package com.raphaelsilva.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphaelsilva.orgs.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product WHERE user_id == :userId")
    fun getAllByUserId(userId: String): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE uid == :uid")
    fun getProductById(uid: Int): Flow<Product?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: Product)

    @Delete
    suspend fun delete(product: Product)
}
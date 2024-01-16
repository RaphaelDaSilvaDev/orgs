package com.raphaelsilva.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphaelsilva.orgs.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM Product WHERE uid == :uid")
    fun getProductById(uid: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: Product)

    @Delete
    fun delete(product: Product)
}
package com.raphaelsilva.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphaelsilva.orgs.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun save(user: User)

    @Query("SELECT * FROM User WHERE id == :username")
    suspend fun getUser(username: String): User
}
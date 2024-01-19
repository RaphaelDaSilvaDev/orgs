package com.raphaelsilva.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.raphaelsilva.orgs.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun save(user: User)

    @Query("SELECT * FROM User WHERE id == :username")
    fun getUser(username: String): Flow<User>

    @Query("SELECT * FROM User WHERE id == :username AND pass == :pass")
    suspend fun authenticateUser(username: String, pass: String): User?
}
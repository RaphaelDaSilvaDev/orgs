package com.raphaelsilva.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raphaelsilva.orgs.database.converter.Converters
import com.raphaelsilva.orgs.database.dao.ProductDao
import com.raphaelsilva.orgs.database.dao.UserDao
import com.raphaelsilva.orgs.model.Product
import com.raphaelsilva.orgs.model.User

@Database(entities = [Product::class, User::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private lateinit var db: AppDatabase
        fun instance(context: Context): AppDatabase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(context, AppDatabase::class.java, "orgs.db")
                .addMigrations(MIGRATION_2_3).build().also {
                    db = it
                }
        }
    }
}
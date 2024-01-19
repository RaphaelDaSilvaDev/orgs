package com.raphaelsilva.orgs.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE IF NOT EXISTS `User` 
            |(`id` TEXT NOT NULL, `name` TEXT NOT NULL,
            | `pass` TEXT NOT NULL, PRIMARY KEY(`id`))""".trimMargin()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Product ADD COLUMN user_id TEXT")
    }
}
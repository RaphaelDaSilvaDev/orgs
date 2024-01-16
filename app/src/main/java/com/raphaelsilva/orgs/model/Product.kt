package com.raphaelsilva.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val image: String? = null
): Parcelable

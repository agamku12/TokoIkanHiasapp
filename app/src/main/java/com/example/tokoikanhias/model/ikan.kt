package com.example.tokoikanhias.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.net.Inet4Address

@Parcelize
@Entity(tableName = "ikan_table")
class ikan (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val jenis: String,
    val address: String
) : Parcelable

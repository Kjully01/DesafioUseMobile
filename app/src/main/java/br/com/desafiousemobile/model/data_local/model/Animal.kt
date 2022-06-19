package br.com.desafiousemobile.model.data_local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "animal_table")
data class Animal (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val age: Int?,
    val species: String?,
    val image: String?
): Parcelable
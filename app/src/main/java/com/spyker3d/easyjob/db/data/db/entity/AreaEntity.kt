package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "area")
data class AreaEntity(
    @PrimaryKey
    val id: String,
    val name: String
)

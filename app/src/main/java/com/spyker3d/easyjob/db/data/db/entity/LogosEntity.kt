package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logos")
data class LogosEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val size90: String?,
    val size240: String?,
    val raw: String?,
    val employerId: Long
)

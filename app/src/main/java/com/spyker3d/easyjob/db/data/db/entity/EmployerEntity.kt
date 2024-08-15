package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employer")
data class EmployerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String?,
    val areaId: String?
)


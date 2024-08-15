package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job_info")
data class JobInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val experience: String?,
    val employment: String?
)

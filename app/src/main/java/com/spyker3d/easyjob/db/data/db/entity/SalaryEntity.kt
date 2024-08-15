package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salary")
data class SalaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val currency: String?,
    val salaryFrom: Int?,
    val gross: Boolean?,
    val salaryTo: Int?,
    val jobInfoId: Long
)

package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val jobInfoId: Long?,
    val employerId: Long,
    val name: String,
    val description: String,
    val vacancyUrl: String,
    val dateAdd: String?
)

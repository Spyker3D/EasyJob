package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class VacancyJoins(
    @Embedded
    val vacancy: VacancyEntity,

    @Relation(
        parentColumn = "jobInfoId",
        entityColumn = "id",
        entity = JobInfoEntity::class
    )
    val jobInfoRow: JobInfoJoins,

    @Relation(
        parentColumn = "employerId",
        entityColumn = "id",
        entity = EmployerEntity::class
    )
    val employerRow: EmployerJoins

)

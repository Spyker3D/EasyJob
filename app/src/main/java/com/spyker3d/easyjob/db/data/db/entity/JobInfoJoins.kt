package com.spyker3d.easyjob.db.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class JobInfoJoins(
    @Embedded
    val jobInfo: JobInfoEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "jobInfoId"
    )
    val skillList: List<SkillsEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "jobInfoId"
    )
    val salaryRow: SalaryEntity?
)

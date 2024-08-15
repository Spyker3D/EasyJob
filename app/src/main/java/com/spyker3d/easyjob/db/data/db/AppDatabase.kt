package com.spyker3d.easyjob.db.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spyker3d.easyjob.db.data.db.entity.AreaEntity
import com.spyker3d.easyjob.db.data.db.entity.EmployerEntity
import com.spyker3d.easyjob.db.data.db.entity.JobInfoEntity
import com.spyker3d.easyjob.db.data.db.entity.LogosEntity
import com.spyker3d.easyjob.db.data.db.entity.SalaryEntity
import com.spyker3d.easyjob.db.data.db.entity.SkillsEntity
import com.spyker3d.easyjob.db.data.db.entity.VacancyEntity

@Database(
    version = 1,
    entities = [
        AreaEntity::class,
        EmployerEntity::class,
        JobInfoEntity::class,
        LogosEntity::class,
        SalaryEntity::class,
        SkillsEntity::class,
        VacancyEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}

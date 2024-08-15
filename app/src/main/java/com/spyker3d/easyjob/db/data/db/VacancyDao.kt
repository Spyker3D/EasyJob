package com.spyker3d.easyjob.db.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import com.spyker3d.easyjob.db.data.db.entity.AreaEntity
import com.spyker3d.easyjob.db.data.db.entity.EmployerEntity
import com.spyker3d.easyjob.db.data.db.entity.EmployerJoins
import com.spyker3d.easyjob.db.data.db.entity.JobInfoEntity
import com.spyker3d.easyjob.db.data.db.entity.JobInfoJoins
import com.spyker3d.easyjob.db.data.db.entity.LogosEntity
import com.spyker3d.easyjob.db.data.db.entity.SalaryEntity
import com.spyker3d.easyjob.db.data.db.entity.SkillsEntity
import com.spyker3d.easyjob.db.data.db.entity.VacancyEntity
import com.spyker3d.easyjob.db.data.db.entity.VacancyJoins

@Dao
abstract class VacancyDao {

    suspend fun deleteVacancyJoins(vacancyJoins: VacancyJoins) {
        deleteEmployerJoins(vacancyJoins.employerRow)

        deleteJobInfoJoins(vacancyJoins.jobInfoRow)

        deleteVacancy(vacancyJoins.vacancy)
    }

    private suspend fun deleteEmployerJoins(employerJoins: EmployerJoins) {
        if (employerJoins.logoRow != null) {
            deleteLogo(employerJoins.logoRow)
        }

        if (employerJoins.employer.areaId != null) {
            deleteArea(employerJoins.employer.areaId)
        }

        deleteEmployer(employerJoins.employer)
    }

    private suspend fun deleteJobInfoJoins(jobInfoJoins: JobInfoJoins) {
        jobInfoJoins.skillList.forEach { skill ->
            deleteSkill(skill)
        }
        if (jobInfoJoins.salaryRow != null) {
            deleteSalary(jobInfoJoins.salaryRow)
        }

        deleteJobInfo(jobInfoJoins.jobInfo)
    }

    suspend fun insertVacancyJoins(vacancy: VacancyJoins) {
        val employerId = insertEmployerJoins(vacancy.employerRow)

        val jobInfoId = insertJobInfoJoins(vacancy.jobInfoRow)

        val vacancyEntity = vacancy.vacancy.copy(
            id = vacancy.vacancy.id,
            jobInfoId = jobInfoId,
            employerId = employerId
        )

        insertVacancy(vacancyEntity)
    }

    private suspend fun insertEmployerJoins(employerJoins: EmployerJoins): Long {
        val employerId = insertEmployer(employerJoins.employer)

        if (employerJoins.logoRow != null) {
            val logosEntity = employerJoins.logoRow.copy(
                id = 0,
                employerId = employerId
            )
            insertLogo(logosEntity)
        }

        insertArea(employerJoins.areaRow)

        return employerId
    }

    private suspend fun insertJobInfoJoins(jobInfoJoins: JobInfoJoins): Long {
        val jobInfoId = insertJobInfo(jobInfoJoins.jobInfo)

        jobInfoJoins.skillList.forEach { skill ->
            val skillsEntity = skill.copy(
                id = 0,
                jobInfoId = jobInfoId
            )

            insertSkill(skillsEntity)
        }
        if (jobInfoJoins.salaryRow != null) {
            val salaryEntity = jobInfoJoins.salaryRow.copy(
                id = 0,
                jobInfoId = jobInfoId
            )

            insertSalary(salaryEntity)
        }

        return jobInfoId
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertArea(areaEntity: AreaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertEmployer(employerEntity: EmployerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLogo(logosEntity: LogosEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSalary(salaryEntity: SalaryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSkill(skillsEntity: SkillsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertJobInfo(jobInfoEntity: JobInfoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertVacancy(vacancyEntity: VacancyEntity)

    @Query("Select 1 from vacancy where id = :vacancyId")
    abstract suspend fun isExistsVacancy(vacancyId: Int): Int?

    @Transaction
    @Query("Select * from vacancy where id = :vacancyId")
    abstract suspend fun getVacancyById(vacancyId: Int): VacancyJoins?

    @Transaction
    @Query("Select * from vacancy order by dateAdd")
    abstract suspend fun getAllVacancy(): List<VacancyJoins>

    @Transaction
    @Query("Select * from vacancy order by dateAdd")
    abstract fun getAllVacancyFlow(): Flow<List<VacancyJoins>>

    @Transaction
    @Query(
        "Select * from vacancy order by dateAdd " +
            "LIMIT :vacancyByPage OFFSET :vacancyByPage * :pageNum "
    )
    abstract suspend fun getAllVacancyByPage(
        pageNum: Int,
        vacancyByPage: Int = VACANCY_COUNT_ON_PAGE
    ): List<VacancyJoins>

    @Delete
    abstract suspend fun deleteJobInfo(jobInfo: JobInfoEntity)

    @Delete
    abstract suspend fun deleteEmployer(employer: EmployerEntity)

    @Delete
    abstract suspend fun deleteSalary(salary: SalaryEntity)

    @Delete
    abstract suspend fun deleteSkill(skill: SkillsEntity)

    @Delete
    abstract suspend fun deleteLogo(logo: LogosEntity)

    @Query(
        "DELETE FROM Area where id = :areaId " +
            "AND not exists (select 1 FROM Employer p1 where p1.areaId = :areaId )"
    )
    abstract suspend fun deleteArea(areaId: String)

    @Delete
    abstract suspend fun deleteVacancy(vacancy: VacancyEntity)

    companion object {
        const val VACANCY_COUNT_ON_PAGE = 20
    }
}

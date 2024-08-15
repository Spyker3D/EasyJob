package com.spyker3d.easyjob.network.data.mapper

import com.spyker3d.easyjob.details.domain.model.AreaDetails
import com.spyker3d.easyjob.details.domain.model.ContactsDetails
import com.spyker3d.easyjob.details.domain.model.EmployerInfo
import com.spyker3d.easyjob.details.domain.model.JobInfo
import com.spyker3d.easyjob.details.domain.model.LogoUrlsDetails
import com.spyker3d.easyjob.details.domain.model.SalaryDetails
import com.spyker3d.easyjob.details.domain.model.VacancyDetails
import com.spyker3d.easyjob.network.data.dto.linked.Contacts
import com.spyker3d.easyjob.network.data.dto.linked.LogoUrls
import com.spyker3d.easyjob.network.data.dto.linked.Salary
import com.spyker3d.easyjob.network.data.dto.responses.VacancyByIdResponse

object VacancyDetailsMapper {
    fun VacancyByIdResponse.mapToDomain(): VacancyDetails {
        return VacancyDetails(
            id = this.id,
            name = this.name,
            description = this.description,
            employerInfo = mapToEmployerInfo(this),
            jobInfo = mapToJobInfo(this),
            vacancyUrl = this.vacancyUrl,
        )
    }

    private fun mapToEmployerInfo(vacancyByIdResponse: VacancyByIdResponse): EmployerInfo {
        return EmployerInfo(
            employerName = vacancyByIdResponse.employer?.name,
            contacts = vacancyByIdResponse.contacts?.mapToDomain(),
            area = AreaDetails(name = vacancyByIdResponse.areaDTO.name, id = vacancyByIdResponse.areaDTO.id),
            logo = vacancyByIdResponse.employer?.logoUrls?.mapToDomain(),
        )
    }

    private fun Contacts.mapToDomain(): ContactsDetails {
        return ContactsDetails(
            email = this.email,
            phones = this.phones?.map { it.formatted },
            name = this.name
        )
    }

    private fun LogoUrls.mapToDomain(): LogoUrlsDetails {
        return LogoUrlsDetails(
            size90 = this.size90,
            size240 = this.size240,
            raw = this.raw
        )
    }

    private fun mapToJobInfo(vacancyByIdResponse: VacancyByIdResponse): JobInfo {
        return JobInfo(
            salary = vacancyByIdResponse.salary?.mapToDomain(),
            experience = vacancyByIdResponse.experience?.name,
            employment = vacancyByIdResponse.employment?.name,
            keySkills = vacancyByIdResponse.keySkills.map { it.name },
        )
    }

    private fun Salary.mapToDomain(): SalaryDetails {
        return SalaryDetails(
            currency = this.currency,
            from = this.from,
            to = this.to,
            gross = this.gross
        )
    }
}

package com.spyker3d.easyjob.network.data.dto.responses

import com.google.gson.annotations.SerializedName
import com.spyker3d.easyjob.network.data.dto.linked.AreaDTO
import com.spyker3d.easyjob.network.data.dto.linked.Contacts
import com.spyker3d.easyjob.network.data.dto.linked.Employer
import com.spyker3d.easyjob.network.data.dto.linked.Employment
import com.spyker3d.easyjob.network.data.dto.linked.Experience
import com.spyker3d.easyjob.network.data.dto.linked.Salary
import com.spyker3d.easyjob.network.data.dto.linked.Skill

class VacancyByIdResponse(
    @SerializedName("description") val description: String,
    @SerializedName("employer") val employer: Employer?,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("salary") val salary: Salary?,
    @SerializedName("contacts") val contacts: Contacts?,
    @SerializedName("area") val areaDTO: AreaDTO,
    @SerializedName("experience") val experience: Experience?,
    @SerializedName("employment") val employment: Employment?,
    @SerializedName("key_skills") val keySkills: List<Skill>,
    @SerializedName("alternate_url") val vacancyUrl: String,
) : Response()

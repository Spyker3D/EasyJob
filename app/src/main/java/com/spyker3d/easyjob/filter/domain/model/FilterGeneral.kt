package com.spyker3d.easyjob.filter.domain.model

data class FilterGeneral(
    val country: CountryFilter? = null,
    val area: AreaFilter? = null,
    val industry: IndustryFilter? = null,
    val expectedSalary: String? = null,
    val hideNoSalaryItems: Boolean? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other !is FilterGeneral) return false
        val salary1 = this.expectedSalary ?: String()
        val salary2 = other.expectedSalary ?: String()
        if (this.area?.areaId.toString() != other.area?.areaId.toString()) return false
        if (this.country?.countryId.toString() != other.country?.countryId.toString()) return false
        if (this.industry?.industryId.toString() != other.industry?.industryId.toString()) return false
        if (this.hideNoSalaryItems != other.hideNoSalaryItems) return false
        if (salary1 != salary2) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

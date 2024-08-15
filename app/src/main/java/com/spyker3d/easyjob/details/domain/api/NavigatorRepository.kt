package com.spyker3d.easyjob.details.domain.api

interface NavigatorRepository {
    fun shareLink(link: String)

    fun sendEmail(email: String)

    fun dialNumber(number: String)
}

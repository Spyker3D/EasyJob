package com.spyker3d.easyjob.details.domain.api

interface NavigatorInteractor {
    fun shareLink(link: String)

    fun sendEmail(email: String)

    fun dialNumber(number: String)
}

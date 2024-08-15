package com.spyker3d.easyjob.details.data.repository

import com.spyker3d.easyjob.details.data.externalnavigator.ExternalNavigator
import com.spyker3d.easyjob.details.domain.api.NavigatorRepository

class NavigatorRepositoryImpl(private val externalNavigator: ExternalNavigator) :
    NavigatorRepository {
    override fun shareLink(link: String) {
        externalNavigator.shareLink(link)
    }

    override fun sendEmail(email: String) {
        externalNavigator.sendEmail(email)
    }

    override fun dialNumber(number: String) {
        externalNavigator.dialNumber(number)
    }

}

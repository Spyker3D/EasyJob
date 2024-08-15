package com.spyker3d.easyjob.details.domain.impl

import com.spyker3d.easyjob.details.domain.api.NavigatorInteractor
import com.spyker3d.easyjob.details.domain.api.NavigatorRepository

class NavigatorInteractorImpl(private val navigatorRepository: NavigatorRepository) :
    NavigatorInteractor {
    override fun shareLink(link: String) {
        navigatorRepository.shareLink(link)
    }

    override fun sendEmail(email: String) {
        navigatorRepository.sendEmail(email)
    }

    override fun dialNumber(number: String) {
        navigatorRepository.dialNumber(number)
    }

}

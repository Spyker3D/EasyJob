package com.spyker3d.easyjob.search.domain.model

data class ContactsModel(
    val email: String?,
    val name: String?,
    val phones: List<PhoneModel>?
)

package com.spyker3d.easyjob.details.data.externalnavigator

import android.content.Context
import android.content.Intent
import android.net.Uri

class ExternalNavigator(private val context: Context) {
    fun shareLink(link: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            context.startActivity(
                Intent.createChooser(this, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    fun sendEmail(email: String) {
        Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    fun dialNumber(number: String) {
        Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$number")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

}

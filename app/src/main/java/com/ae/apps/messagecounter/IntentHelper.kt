package com.ae.apps.messagecounter

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.ae.apps.messagecounter.services.SMSObserverService
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity


fun getShareIntent(context: Context): Intent {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.app_play_store_url))
    shareIntent.putExtra(Intent.EXTRA_TITLE, context.getString(R.string.menu_title_share))
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
    return shareIntent
}

fun getFeedbackIntent(context: Context): Intent {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.type = "text/html"
    intent.data = Uri.parse("mailto:" + context.getString(R.string.app_feedback_email_address))
    intent.putExtra(Intent.EXTRA_EMAIL, context.getString(R.string.app_feedback_email_address))
    intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.str_about_share_feedback_subject))
    intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.str_about_share_feedback_body))
    return intent
}

fun getOpenSourceLicenceDisplayIntent(context: Context): Intent {
    return Intent(context, OssLicensesMenuActivity::class.java)
}

fun getViewSourceIntent(context: Context): Intent {
    return createIntentForURI(context.getString(R.string.app_github_source_url))
}

fun getViewFaqIntent(context: Context): Intent {
    return createIntentForURI(context.getString(R.string.app_faq_url))
}

fun getMessageCounterServiceIntent(context:Context):Intent{
    return Intent(context, SMSObserverService::class.java)
}

private fun createIntentForURI(url: String): Intent {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    return intent
}

@SuppressWarnings("deprecation")
fun isServiceRunning(context:Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}
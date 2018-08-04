package com.ae.apps.messagecounter.services

import android.annotation.TargetApi
import android.app.job.JobScheduler
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.widget.Toast
import com.ae.apps.messagecounter.data.preferences.PreferenceRepository
import com.ae.apps.messagecounter.getMessageCounterServiceIntent

/**
 * Helper class that runs a BackgroundService for lower than Nougat
 * and a JobService using JobScheduler for Nougat and up
 *
 * The BackgroundService is started by respecting the enable background service
 * preference
 */
class CounterServiceHelper {

    companion object {

        fun monitorMessagesInBackground(context: Context) {
            val preferenceRepository = PreferenceRepository.newInstance(
                    PreferenceManager.getDefaultSharedPreferences(context))
            // Run the service or job only if we have the required permissions
            if (preferenceRepository.hasRuntimePermissions()) {
                // Use a JobService to detect SMS database changes in Nougat and up
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val result = com.ae.apps.messagecounter.services.CounterJobService.registerJob(context)
                    Toast.makeText(context, "Starting JobService: $result", Toast.LENGTH_SHORT).show()
                } else {
                    // Use a background service up to Marshmallow
                    if (preferenceRepository.backgroundServiceEnabled()) {
                        context.startService(getMessageCounterServiceIntent(context))
                    } else {
                        context.stopService(getMessageCounterServiceIntent(context))
                    }
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun isJobRunning(scheduler: JobScheduler, jobId: Int): Boolean {
            for (jobInfoTemp in scheduler.allPendingJobs) {
                if (jobInfoTemp.id == jobId) {
                    return true
                }
            }
            return false
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun isJobNotRunning(scheduler: JobScheduler, jobId: Int): Boolean {
            return !isJobRunning(scheduler, jobId)
        }

    }
}
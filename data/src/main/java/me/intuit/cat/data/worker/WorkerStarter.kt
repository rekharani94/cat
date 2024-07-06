package me.intuit.cat.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerStarter(context : Context) {

     val workManager = WorkManager.getInstance(context)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED) // Require network connectivity
        .build()

    companion object {
        const val WORK_MANAGER_SYNC_TAG = "WORK_MANAGER_SYNC_TAG"
    }

    operator fun invoke(){
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag(WORK_MANAGER_SYNC_TAG)
            .build()

       /* workManager
            .enqueueUniquePeriodicWork(
                WORK_MANAGER_SYNC_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )*/
        workManager.enqueueUniqueWork(
            SyncWorker::class.java.simpleName,
            ExistingWorkPolicy.KEEP,
            syncRequest
        )
    }

    private fun isWorkerAlreadyRunning(): Boolean {
        val workInfo = workManager.getWorkInfosByTag(WORK_MANAGER_SYNC_TAG).get()
        workInfo.forEach { work ->
            if(work.state == WorkInfo.State.ENQUEUED || work.state == WorkInfo.State.RUNNING)
                return true
        }
        return false
    }
}
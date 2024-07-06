package me.intuit.cat.presentation.synchonization

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.intuit.cat.domain.repository.BreedsRepository
import me.intuit.cat.domain.util.getResult
import me.intuit.cat.domain.util.onError
import java.util.concurrent.CancellationException
import javax.inject.Inject
@HiltWorker
class SyncWorker @AssistedInject constructor(private val breedsRepository : BreedsRepository,@Assisted appContext : Context,
                                             @Assisted workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {
                                                 var SYNC_WORK_MAX_ATTEMPTS =3
@Inject
   // @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
override suspend fun doWork(): Result {
    return withContext(Dispatchers.IO){
        try {
            if(breedsRepository.sync()) {
                Log.e("syncworker","worker success")
                return@withContext Result.success()
            }
            return@withContext Result.retry()

        }
        catch (e:Exception) {
            Log.e("syncworker","exception ${e.message}")

            val lastAttempt = runAttemptCount >= SYNC_WORK_MAX_ATTEMPTS
            if (lastAttempt) {
                Log.d("XXX", "SyncWork: doWork() called -> failure")
                Result.failure()
            }
            else if (e is CancellationException) {
                Log.d("XXX", "SyncWork: doWork() exception called -> retry")
                Result.retry()

                // Handle cancellation gracefully
                // ... (e.g., save progress, clean up resources)
            }
            else {
                Log.d("XXX", "SyncWork: doWork() called -> retry")
                Result.retry()
            }

            return@withContext Result.retry()

        }
    }

}

}


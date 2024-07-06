package me.intuit.cat.presentation.synchonization

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import me.intuit.cat.domain.repository.BreedsRepository
import androidx.work.WorkerParameters
import javax.inject.Inject
import me.intuit.cat.data.worker.SyncWorker

class CustomWorkerFactory @Inject constructor(
    private val repository: BreedsRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SyncWorker(repository,appContext,workerParameters)
}
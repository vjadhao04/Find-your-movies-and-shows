package com.example.moviesapp.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker(val context: Context, val workerParams: WorkerParameters) :Worker(context, workerParams) {
    override fun doWork(): Result {
        //println("worker doing work")
        Log.d("workmaneger","Worker doing work")

        return Result.success()
    }
}
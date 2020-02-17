package org.wit.plannerapp.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.plannerapp.models.PlannerappJSONStore
import org.wit.plannerapp.models.PlannerappStore

class PlannerApp : Application(), AnkoLogger {


    lateinit var plannerapps: PlannerappStore

    override fun onCreate() {
        super.onCreate()
        plannerapps =
            PlannerappJSONStore(applicationContext)
        info("Planner started")

    }
}
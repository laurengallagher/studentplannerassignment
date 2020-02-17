package org.wit.plannerapp.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PlannerappMemStore : PlannerappStore, AnkoLogger {

    val plannerapps = ArrayList<PlannerappModel>()

    override fun findAll(): List<PlannerappModel> {
        return plannerapps
    }

    override fun create(plannerapp: PlannerappModel) {
        plannerapp.id = getId()
        plannerapps.add(plannerapp)
        logAll()
    }

    override fun update(plannerapp: PlannerappModel) {
        var foundPlannerapp: PlannerappModel? = plannerapps.find { p -> p.id == plannerapp.id }
        if (foundPlannerapp != null) {
            foundPlannerapp.title = plannerapp.title
            foundPlannerapp.description = plannerapp.description
            foundPlannerapp.image = plannerapp.image
            foundPlannerapp.lat = plannerapp.lat
            foundPlannerapp.lng = plannerapp.lng
            foundPlannerapp.zoom = plannerapp.zoom
            logAll();
        }
    }

    override fun delete(plannerapp: PlannerappModel) {
        plannerapps.remove(plannerapp)
    }

    fun logAll() {
        plannerapps.forEach{ info("${it}")}
    }
}
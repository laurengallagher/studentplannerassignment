package org.wit.plannerapp.models

interface PlannerappStore {
    fun findAll(): List<PlannerappModel>
    fun create(plannerapp: PlannerappModel)
    fun update(plannerapp: PlannerappModel)
    fun delete(plannerapp: PlannerappModel)
}
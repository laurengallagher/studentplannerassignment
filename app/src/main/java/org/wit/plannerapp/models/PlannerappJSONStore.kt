package org.wit.plannerapp.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.plannerapp.helpers.exists
import org.wit.plannerapp.helpers.read
import org.wit.plannerapp.helpers.write
import java.util.*

val JSON_FILE = "plannerapps.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<PlannerappModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PlannerappJSONStore : PlannerappStore, AnkoLogger {

    val context: Context
    var plannerapps = mutableListOf<PlannerappModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(
                context,
                JSON_FILE
            )
        ) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PlannerappModel> {
        return plannerapps
    }

    override fun create(plannerapp: PlannerappModel) {
        plannerapp.id = generateRandomId()
        plannerapps.add(plannerapp)
        serialize()
    }


    override fun update(plannerapp: PlannerappModel) {
        val plannerappsList = findAll() as ArrayList<PlannerappModel>
        var foundPlannerapp: PlannerappModel? = plannerappsList.find { p -> p.id == plannerapp.id }
        if (foundPlannerapp != null) {
            foundPlannerapp.title = plannerapp.title
            foundPlannerapp.description = plannerapp.description
            foundPlannerapp.image = plannerapp.image
            foundPlannerapp.lat = plannerapp.lat
            foundPlannerapp.lng = plannerapp.lng
            foundPlannerapp.zoom = plannerapp.zoom
        }
        serialize()
    }

    override fun delete(plannerapp: PlannerappModel) {
        plannerapps.remove(plannerapp)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(plannerapps,
            listType
        )
        write(
            context,
            JSON_FILE,
            jsonString
        )
    }

    private fun deserialize() {
        val jsonString = read(
            context,
            JSON_FILE
        )
        plannerapps = Gson().fromJson(jsonString,
            listType
        )
    }
}
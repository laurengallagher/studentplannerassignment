package org.wit.plannerapp.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_main.view.*
import kotlinx.android.synthetic.main.card_main.view.description
import org.wit.plannerapp.R
import org.wit.plannerapp.helpers.readImageFromPath
import org.wit.plannerapp.models.PlannerappModel


interface PlannerappListener {
    fun onPlannerappClick(plannerapp: PlannerappModel)
}


class MainAdapter constructor(private var plannerapps: List<PlannerappModel>,
                              private val listener: PlannerappListener
) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(R.layout.card_main, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val plannerapp = plannerapps[holder.adapterPosition]
        holder.bind(plannerapp, listener)
    }

    override fun getItemCount(): Int = plannerapps.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(plannerapp: PlannerappModel, listener : PlannerappListener) {
            itemView.plannerTitle.text = plannerapp.title
            itemView.description.text = plannerapp.description
            itemView.imageIcon.setImageBitmap(
                readImageFromPath(
                    itemView.context,
                    plannerapp.image
                )
            )
            itemView.setOnClickListener { listener.onPlannerappClick(plannerapp) }
        }
    }
}
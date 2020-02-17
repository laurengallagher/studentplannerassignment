package org.wit.plannerapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_main_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.plannerapp.R
import org.wit.plannerapp.main.PlannerApp
import org.wit.plannerapp.models.PlannerappModel


class MainListActivity : AppCompatActivity(),
    PlannerappListener {

    lateinit var app: PlannerApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        app = application as PlannerApp
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MainAdapter(
            app.plannerapps.findAll(),
            this
        )
        loadPlannerapps()
    }

    private fun loadPlannerapps() {
        showPlannerapps(app.plannerapps.findAll())
    }

    fun showPlannerapps (plannerapps: List<PlannerappModel>) {
        recyclerView.adapter =
            MainAdapter(plannerapps, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MainActivity>(0)
            R.id.item_map -> startActivity<MainMapsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlannerappClick(plannerapp: PlannerappModel) {
        startActivityForResult(intentFor<MainActivity>().putExtra("plannerapp_edit", plannerapp), 0)
    }


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         loadPlannerapps()
         super.onActivityResult(requestCode, resultCode, data)
  }
    
}


    


    


    

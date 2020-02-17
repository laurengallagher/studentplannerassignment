package org.wit.plannerapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.plannerapp.R
import org.wit.plannerapp.helpers.readImage
import org.wit.plannerapp.helpers.readImageFromPath
import org.wit.plannerapp.helpers.showImagePicker
import org.wit.plannerapp.main.PlannerApp
import org.wit.plannerapp.models.Location
import org.wit.plannerapp.models.PlannerappModel


class MainActivity : AppCompatActivity(), AnkoLogger {

    var plannerapp = PlannerappModel()
    lateinit var app: PlannerApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var edit = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Planner Activity started..")

        app = application as PlannerApp


        if (intent.hasExtra("plannerapp_edit")) {
            edit = true
            plannerapp = intent.extras.getParcelable<PlannerappModel>("plannerapp_edit")
            plannerTitle.setText(plannerapp.title)
            description.setText(plannerapp.description)
            plannerappImage.setImageBitmap(
                readImageFromPath(
                    this,
                    plannerapp.image
                )
            )
            if (plannerapp.image != null) {
                chooseImage.setText(R.string.change_plannerapp_image)
            }
            btnAdd.setText("Save Plan")
        }


        btnAdd.setOnClickListener() {
            plannerapp.title = plannerTitle.text.toString()
            plannerapp.description = description.text.toString()
            if (plannerapp.title.isEmpty()) {
                toast("Please enter your Plan")
            } else {
                if (edit) {
                    app.plannerapps.update(plannerapp.copy())
                } else {
                    app.plannerapps.create(plannerapp.copy())
                }
            }
            info("add Button Pressed: $plannerTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }


        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        plannerappLocation.setOnClickListener {
            val location =
                Location(52.245696, -7.139102, 15f)
            if (plannerapp.zoom != 0f) {
                location.lat = plannerapp.lat
                location.lng = plannerapp.lng
                location.zoom = plannerapp.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.plannerapps.delete(plannerapp)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    plannerapp.image = data.getData().toString()
                    plannerappImage.setImageBitmap(
                        readImage(
                            this,
                            resultCode,
                            data
                        )
                    )
                    chooseImage.setText(R.string.change_plannerapp_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    plannerapp.lat = location.lat
                    plannerapp.lng = location.lng
                    plannerapp.zoom = location.zoom
                }
            }
        }
    }
}







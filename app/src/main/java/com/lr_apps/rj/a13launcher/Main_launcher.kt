package com.lr_apps.rj.a13launcher

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main_launcher.*

class Main_launcher : AppCompatActivity(), View.OnClickListener {


    private var btnMyDock:Button?=null /**Boton abrir dock*/
    /**-----------------------------Pantalla Principal-------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_launcher)
        btnMyDock=findViewById(R.id.btnDock) as Button
        btnMyDock!!.setOnClickListener(this)


    }
    /**--------------------------Evento al hacer click en el boton de abrir dock*/
    override fun onClick(v: View?) {
        openAppDrawer()
    }
    /**--------------------------Metodo que despliega la lista----------------------*/
    private fun openAppDrawer(){ //Open Drawer Method
        val intent = Intent(this,AppsList_Activity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

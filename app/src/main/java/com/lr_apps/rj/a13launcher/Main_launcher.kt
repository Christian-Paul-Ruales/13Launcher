package com.lr_apps.rj.a13launcher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_main_launcher.*

class Main_launcher : AppCompatActivity(){


    private var btnMyDock:Button?=null /**Boton abrir dock*/
    private var manager: PackageManager?=null /**Manejar las apps*/
    private var Apps:ArrayList<Item>?=null /**Lista de apps*/
    private var ListDockApps: GridView?=null /**Listview para mostrar las apps(grafico)*/


    /**-----------------------------Pantalla Principal-------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_launcher)
        LoadApps()
        LoadListView()
        onClickListener()
        onSwipeTop()

    }
    /**--------------------------Evento al hacer click en el boton de abrir el AppDrawer*/

    /**--------------------------Metodo que despliega la lista----------------------*/
    private fun openAppDrawer(){ //Open Drawer Method
        val intent = Intent(this,AppsList_Activity::class.java)
        startActivity(intent)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun LoadApps(){

        manager=packageManager
        Apps=ArrayList<Item>()
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        var availableActivities:List<ResolveInfo> = manager!!.queryIntentActivities(intent,0) /**Toma apps del telefono*/

        /**-------------------------Guardar Datos de las apps en una lista-----------------*/
        for (r1 in availableActivities){
            var app:Item = Item()
            app!!.label=r1.activityInfo.packageName

            app!!.name=r1.loadLabel(manager)

            app!!.icon = r1.loadIcon(manager)

            if (app!!.name=="Phone"){
            Apps!!.add(app)
            }
            if (app!!.label=="com.android.browser"){
                Apps!!.add(app)
            }
            if (app!!.label=="com.android.mms"){
                Apps!!.add(app)
            }
            if (app!!.name=="Music"){
                Apps!!.add(app)
            }


        }
    }


    private fun LoadListView(){
        ListDockApps=findViewById(R.id.ListDock) as GridView
        var adapter=CustomAdapter(applicationContext,Apps)
        ListDockApps!!.adapter=CustomAdapter(applicationContext, Apps) /**Adapter Personalizado CustomAdapter*/

    }

    private fun onClickListener() {
        ListDockApps!!.setOnItemClickListener { Adapter: AdapterView<*>?, view: View?, position: Int, id: Long ->
            var intent: Intent = manager!!.getLaunchIntentForPackage(Apps!!.get(position).label.toString())
            startActivity(intent)
        }
    }

    private fun onSwipeTop(){
        ListDockApps!!.setOnTouchListener(object: MyOnSwipeTouchListener(this){

            override fun onSwipeTop() {
                //super.onSwipeTop()
                openAppDrawer()

            }

        })


    }
}

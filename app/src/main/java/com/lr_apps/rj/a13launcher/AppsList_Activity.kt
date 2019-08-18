package com.lr_apps.rj.a13launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import android.support.v7.widget.LinearLayoutManager



class AppsList_Activity : AppCompatActivity() {
    private var manager:PackageManager?=null /**Manejar las apps*/
    private var Apps:ArrayList<Item>?=null /**Lista de apps*/
    private var AppsListView:GridView?=null /**Listview para mostrar las apps(grafico)*/

    //Metodo Creacion, Similar a un constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list_)
        LoadApps()
        LoadListView()
        onClickListener()
    }

    /**----------------------------Metodo Cargar Apps -----------------------*/
    /**No tomar en cuenta los print*/
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
            print("++++++++++++++++++++++++++++++++app.label:"+app!!.label+"\n\n\n")
            print("+-------------------------r1.activityinfo.pakagename: "+r1.activityInfo.packageName+"\n\n\n\n")

            app!!.name=r1.loadLabel(manager)
            print("++++++++++++++++++++++++++++++++app.label:"+app!!.name+"\n\n\n")
            print("+-------------------------r1.activityinfo.loadlabel: "+r1.loadLabel(manager)+"\n\n\n\n")

            app!!.icon = r1.loadIcon(manager)
            print("++++++++++++++++++++++++++++++++app.icon:"+app!!.icon+"\n\n\n")
            print("+-------------------------r1.activityinfo.loadicon: "+r1.loadIcon(manager)+"\n\n\n\n")

            print("++++++++++++++++++++++++++++++++app: "+app+"\n\n\n\n")
            Apps!!.add(app)

        }
    }

    /**----------------------------------Metodo cargar apps en la Lista----------*/
    private fun LoadListView(){


        AppsListView=findViewById(R.id.ListAppDrawer) as GridView


        var adapter=CustomAdapter(applicationContext,Apps)
        AppsListView!!.adapter=CustomAdapter(applicationContext, Apps) /**Adapter Personalizado CustomAdapter*/


    }

    /**--------------------------------Accion al hacer click en un elemento de la lista*/
    private fun onClickListener(){
        AppsListView!!.setOnItemClickListener{Adapter: AdapterView<*>?, view: View?, position: Int, id: Long ->
            var intent:Intent =manager!!.getLaunchIntentForPackage(Apps!!.get(position).label.toString())
            startActivity(intent)
        }



    }


}

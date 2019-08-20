package com.lr_apps.rj.a13launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.*
import java.util.jar.Attributes


class AppsList_Activity : AppCompatActivity() {
    private var manager:PackageManager?=null /**Manejar las apps*/
    private var Apps:ArrayList<Item>?=null /**Lista de apps*/
    private var AppsListView:GridView?=null /**Listview para mostrar las apps(grafico)*/
    private var searchAppsView:SearchView?=null

    //Metodo Creacion, Similar a un constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list_)
        LoadApps()
        LoadListView()
        onClickListener()
        OntextChange()
    }

    /**----------------------------Metodo Cargar Apps -----------------------*/
    /**No tomar en cuenta los print*/
    private fun LoadApps(){

        manager=packageManager
        Apps=ArrayList<Item>()
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        var availableActivities:List<ResolveInfo> = manager!!.queryIntentActivities(intent,0) /**Toma apps del telefono*/
        val sortedAppsList = availableActivities.sortedBy { it.loadLabel(manager)?.toString() }
        availableActivities = sortedAppsList

        /**-------------------------Guardar Datos de las apps en una lista-----------------*/
        for (r1 in availableActivities){
            var app:Item = Item()
            app!!.label=r1.activityInfo.packageName

            app!!.name=r1.loadLabel(manager)

            app!!.icon = r1.loadIcon(manager)

                Apps!!.add(app)



        }

    }

    private fun FindApps(find:String){

        manager=packageManager
        Apps=ArrayList<Item>()
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        var availableActivities:List<ResolveInfo> = manager!!.queryIntentActivities(intent,0) /**Toma apps del telefono*/
        val sortedAppsList = availableActivities.sortedBy { it.loadLabel(manager)?.toString() }
        availableActivities = sortedAppsList

        /**-------------------------Guardar Datos de las apps en una lista-----------------*/
        for (r1 in availableActivities){
            var app:Item = Item()
            app!!.label=r1.activityInfo.packageName
            app!!.name=r1.loadLabel(manager)
            app!!.icon = r1.loadIcon(manager)

            if(find.equals("")){
                Apps!!.add(app)
            }else{
                var NameAplication=app.name.toString().split(" ")

                if(find.contains(" ")){
                    if(NameAplication.size<=2){
                        if(app.name.toString().startsWith(find,ignoreCase =true)){
                            Apps!!.add(app)
                        }
                    }else{
                        var sizeSearched=find.split(" ").size

                        for (num in 0..(NameAplication.size-sizeSearched)){
                                var new_word=""
                                for(n2 in 0 until sizeSearched){

                                        if(n2==0){
                                            new_word=NameAplication[num+n2]
                                        }else{
                                            new_word=new_word+" "+NameAplication[num+n2]
                                        }

                                }

                                if(new_word.startsWith(find,ignoreCase =true)){
                                    Apps!!.add(app)
                                }


                        }
                    }
                }else{
                        for (part_name in NameAplication){
                            if(part_name.startsWith(find,ignoreCase =true)){
                                Apps!!.add(app)
                            }
                        }

                }


            }


        }

    }

    /**----------------------------------Metodo cargar apps en la Lista----------*/
    private fun LoadListView(){


        AppsListView=findViewById(R.id.ListAppDrawer) as GridView


        AppsListView!!.adapter=CustomAdapter(applicationContext, Apps) /**Adapter Personalizado CustomAdapter*/


    }
    private fun LoadAppsFinded(){


        AppsListView=findViewById(R.id.ListAppDrawer) as GridView


        AppsListView!!.adapter=CustomAdapter(applicationContext, Apps) /**Adapter Personalizado CustomAdapter*/


    }

    /**--------------------------------Accion al hacer click en un elemento de la lista*/
    private fun onClickListener(){
        AppsListView!!.setOnItemClickListener{Adapter: AdapterView<*>?, view: View?, position: Int, id: Long ->
            var intent:Intent =manager!!.getLaunchIntentForPackage(Apps!!.get(position).label.toString())
            startActivity(intent)

        }



    }

    private fun OntextChange() {
        searchAppsView=findViewById(R.id.SearchApps) as SearchView

        searchAppsView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                FindApps(newText)
                LoadAppsFinded()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                print(" =========================Query:"+query)
                return true
            }

        })
    }




}

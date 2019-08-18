package com.lr_apps.rj.a13launcher

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
/** Adapter personalizado: Hacer cambios en la lista*/
class CustomAdapter(var context: Context, var apps:ArrayList<Item>?):BaseAdapter() {


    private class ViewHolder(row: View?){
        var nameApp: TextView//app name
        var iconApp: ImageView// app icon
        init {
            this.nameApp=row?.findViewById(R.id.name) as TextView /** res\layout*/
            this.iconApp=row?.findViewById(R.id.image) as ImageView

        }
    }
    /**Retorna la vista: pone imagen y nombre en la lista*/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if(convertView==null){
            var layout=LayoutInflater.from(context)
            view=layout.inflate(R.layout.item,parent,false)
            viewHolder=ViewHolder(view)
            view.tag=viewHolder
        }else{
            view =convertView
            viewHolder=view.tag as ViewHolder
        }
        var item:Item=getItem(position) as Item
        viewHolder.nameApp.text=item.name
        viewHolder.iconApp.setImageDrawable(item.icon)
        return view as View
    }

    /**Retorna item*/
    override fun getItem(position: Int): Any {
        return apps!!.get(position)
    }
    /**Retorna id del item*/
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**Retorna count*/
    override fun getCount(): Int {
        return apps!!.count()
    }
}
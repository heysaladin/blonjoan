package com.saladinid.blonjoan.handler

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import com.saladinid.blonjoan.R

class ListAdapter(private val mContext: Context, private val vg: Int, id: Int, private val list: ArrayList<JSONObject>) : ArrayAdapter<JSONObject>(mContext, vg, id, list) {

    private val myButtonClickListener = View.OnClickListener { v ->
        val parentRow = v.parent as View
        val listView = parentRow.parent as ListView
        val position = listView.getPositionForView(parentRow)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("ViewHolder") var itemView: View? = null
        if (inflater != null) {
            itemView = inflater.inflate(vg, parent, false)
        }
        val txtRestaurantTitle: TextView
        txtRestaurantTitle = itemView!!.findViewById<View>(R.id.karma_resorts_group_title) as TextView
        val txtRestaurant = itemView.findViewById<View>(R.id.karma_resorts_item) as TextView
        try {
            txtRestaurant.text = list[position].getString("name")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return itemView

    }

    override fun isEnabled(position: Int): Boolean {
        var returnVal = true
        returnVal = true
        return returnVal
    }

    companion object {

        private val TAG = "ListAdapter"
    }

}
package com.saladinid.blonjoan.handler

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.activity.EditGroceryActivity
import com.saladinid.blonjoan.data.GroceriesModel
import com.saladinid.blonjoan.data.ItemsModel
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList

class GroceryLineDetailsAdapter(private val mContext: Context, private val mFlowerList: List < GroceriesModel > ? ): RecyclerView.Adapter < PlanLineDetailsViewHolder > () {

    private
    var dataDestinations: JSONArray ? = null
    private
    var itemsArrayListBuffer: List < ItemsModel > ? = null
    private
    var itemsArrayList: ArrayList < ItemsModel > ? = null
    private
    var imageUrl: String ? = null
    private
    var mRecyclerView: RecyclerView ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLineDetailsViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row_grocery_inline_details, parent, false)
        return PlanLineDetailsViewHolder(mView)
    }

    override fun onBindViewHolder(holder: PlanLineDetailsViewHolder, position: Int) {

        mRecyclerView = holder.recyclerview

        holder.card_view_image_title.text = mFlowerList!![position].title

        holder.mTitle.text = mFlowerList!![position].title
        holder.mCardView.setOnClickListener {
            val mIntent = Intent(mContext, EditGroceryActivity::class.java)
            mIntent.putExtra("_id", mFlowerList[holder.adapterPosition]._id)
            mIntent.putExtra("title", mFlowerList[holder.adapterPosition].title)
            mIntent.putExtra("items", mFlowerList[holder.adapterPosition].items.toString())
            mContext.startActivity(mIntent)
        }

        //getKarmaGroupsApiRequest()

        dataDestinations = mFlowerList!![position].items
        Log.d("dataDestinations", dataDestinations.toString())
        if(dataDestinations!!.length() > 0) {
            processData()
        }

    }

    override fun getItemCount(): Int {
        return mFlowerList!!.size
    }

    private fun processData() {
        try {
            val dataJson = dataDestinations
            itemsArrayListBuffer = ArrayList < ItemsModel > ()
            itemsArrayList = ArrayList < ItemsModel > ()
            //mRecyclerView = recyclerviewval;//findViewById(R.id.recyclerview)
            // val mGridLayoutManager = GridLayoutManager(this@ListItemsActivity, 2)
            val mLinearLayoutManager = LinearLayoutManager(this@GroceryLineDetailsAdapter.mContext)
            mRecyclerView!!.layoutManager = mLinearLayoutManager!!
            itemsArrayList!!.clear()
            val dma = ArrayList < ItemsModel > ()
            dma.clear()
            for (j in 0 until dataJson!!.length()) {
                val job = dataJson.getJSONObject(j)
                val model = ItemsModel(
                        job.optString("_id"),
                        job.optString("name"),
                        job.optString("image"),
                        job.optString("category"),
                        job.optString("unit"),
                        job.optString("price")
                )
                dma.add(model)
                itemsArrayList!!.add(model)
            }
            itemsArrayListBuffer = itemsArrayList
            val myAdapter = itemsArrayListBuffer?.let {
                ItemLineDetailsAdapter(this@GroceryLineDetailsAdapter.mContext, it)
            }
            mRecyclerView!!.adapter = myAdapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

//    private fun getKarmaGroupsApiRequest() {
//        val linkTrang = "http://familygroceries.herokuapp.com/items"
//        val queue = Volley.newRequestQueue(this@GroceryLineDetailsAdapter.mContext)
//        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
//                Response.Listener < String > {
//                    response ->
//                    Log.d("TAG", response.toString())
//                    dataDestinations = JSONArray(response)
//                    Log.d("dataDestinations", dataDestinations.toString())
//                    processData()
//                },
//                Response.ErrorListener {}) {
//            override fun getHeaders(): MutableMap < String, String > {
//                val headers = HashMap < String, String > ()
//                return headers
//            }
//        }
//        queue.add(stringRequest)
//    }

//    override fun onResume() {
//        super.onResume()
//        getKarmaGroupsApiRequest()
//    }


}

class PlanLineDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var mImage: ImageView
    var mTitle: TextView
    var mCardView: CardView
    var card_view_image_title: TextView
    var recyclerview: RecyclerView

    init {
        mImage = itemView.findViewById(R.id.ivImage)
        mTitle = itemView.findViewById(R.id.tvTitle)
        mCardView = itemView.findViewById(R.id.cardview)
        card_view_image_title = itemView.findViewById(R.id.card_view_image_title)
        recyclerview = itemView.findViewById(R.id.recyclerview)
    }

}
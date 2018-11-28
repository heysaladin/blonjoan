package com.saladinid.blonjoan.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.bumptech.glide.Glide
import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.data.ItemsModel
import com.saladinid.blonjoan.handler.MyAdapter

import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList
import android.support.v7.widget.GridLayoutManager

class ListPromosActivity: AppCompatActivity() {

    private
    var mToolbar: Toolbar ? = null
    private
    var ivImage: ImageView ? = null
    private
    var tvDescription: TextView ? = null
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

    private fun getIntentData() {
        val intent = intent
        imageUrl = intent.getStringExtra("Image")
    }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        getIntentData()
        getKarmaGroupsApiRequest()
        mToolbar = findViewById(R.id.toolbar)
        mToolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        mToolbar!!.setNavigationOnClickListener {
            onBackPressed()
        }
        mToolbar!!.title = "Notifications"
        val mBundle = intent.extras
        if (mBundle != null) {
            mToolbar!!.title = mBundle.getString("Title")
            if (imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(ivImage!!)
            } else {
                ivImage!!.setImageResource(mBundle.getInt("Image"))
            }
            tvDescription!!.text = mBundle.getString("title")
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_account_box_black_24dp)
            mToolbar!!.overflowIcon = drawable
        }
    }

    private fun processData() {
        try {
            val dataJson = dataDestinations
            itemsArrayListBuffer = ArrayList < ItemsModel > ()
            itemsArrayList = ArrayList < ItemsModel > ()
            mRecyclerView = findViewById(R.id.recyclerview)
            val mGridLayoutManager = GridLayoutManager(this@ListPromosActivity, 2)
            mRecyclerView!!.layoutManager = mGridLayoutManager
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
                MyAdapter(this@ListPromosActivity, it)
            }
            mRecyclerView!!.adapter = myAdapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getKarmaGroupsApiRequest() {
        val linkTrang = "http://familygroceries.herokuapp.com/items"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
                Response.Listener < String > {
                    response ->
                    Log.d("TAG", response.toString())
                    dataDestinations = JSONArray(response)
                    Log.d("dataDestinations", dataDestinations.toString())
                    processData()
                },
                Response.ErrorListener {}) {
            override fun getHeaders(): MutableMap < String, String > {
                val headers = HashMap < String, String > ()
                return headers
            }
        }
        queue.add(stringRequest)
    }

    override fun onResume() {
        super.onResume()
        getKarmaGroupsApiRequest()
    }

}
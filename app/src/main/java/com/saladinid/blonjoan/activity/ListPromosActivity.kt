package com.saladinid.blonjoan.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
//import com.codingdemos.vacapedia.data.ItemsModel
import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.data.ItemsModel
import com.saladinid.blonjoan.handler.MyAdapter
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
//import com.codingdemos.vacapedia.data.PromosModel
//import com.codingdemos.vacapedia.handlers.PromosAdapter
//import com.codingdemos.vacapedia.handlers.SliderAdapter
//import com.codingdemos.vacapedia.rest.AsyncHttpResponse
//import com.codingdemos.vacapedia.rest.RestApis
//import com.loopj.android.http.RequestParams

import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList

class ListPromosActivity : AppCompatActivity()
//        , AsyncHttpResponse.AsyncHttpResponseListener
{

    private var mToolbar: Toolbar? = null
    private var ivImage: ImageView? = null
    private var tvDescription: TextView? = null
    private var dataDestinations: JSONArray? = null
    private var itemsArrayListBuffer: List<ItemsModel>? = null
    private var itemsArrayList: ArrayList<ItemsModel>? = null
    private var imageUrl: String? = null
    private var mRecyclerView: RecyclerView? = null

    private fun getIntentData() {
        val intent = intent
        imageUrl = intent.getStringExtra("Image")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        getIntentData()



//        val service = ServiceVolley()
//        val apiController = APIController(service)
//        val path: String = "http://familygroceries.herokuapp.com/items"
//        apiController.get(path) { response -> dataDestinations
//            // Parse the result
//            Log.d("TAG", response.toString())
////            dataDestinations = JSONArray(response)
//            Log.d("dataDestinations", dataDestinations.toString())
//            processData()
//        }

        val linkTrang = "http://familygroceries.herokuapp.com/items"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
                Response.Listener<String> { response ->
//                    Log.d("A", "Response is: " + response.substring(0,500))

                    Log.d("TAG", response.toString())
                    dataDestinations = JSONArray(response)
                    Log.d("dataDestinations", dataDestinations.toString())
                    processData()

                },
                Response.ErrorListener {  })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
//                headers["Authorization"] = "Basic <<YOUR BASE64 USER:PASS>>"
                return headers
            }
        }

        queue.add(stringRequest)



//        getKarmaGroupsApiRequest()
        mToolbar = findViewById(R.id.toolbar)
//        mFlower = findViewById(R.id.ivImage)
        mToolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        mToolbar!!.setNavigationOnClickListener { onBackPressed() }
//        mDescription = findViewById(R.id.tvDescription)
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
            itemsArrayListBuffer = ArrayList<ItemsModel>()
            itemsArrayList = ArrayList<ItemsModel>()
            mRecyclerView = findViewById(R.id.recyclerview)
            val mLinearLayoutManager = LinearLayoutManager(this@ListPromosActivity)
            mRecyclerView!!.layoutManager = mLinearLayoutManager
            itemsArrayList!!.clear()
            val dma = ArrayList<ItemsModel>()
            dma.clear()
            for (j in 0 until dataJson!!.length()) {
                val job = dataJson.getJSONObject(j)
//                val model = ItemsModel()
//                model.set_id(job.optString("_id"))
//                model.setTitle(job.optString("name"))
//                model.setImage(job.optString("image"))
//                model.setCategory(job.optString("category"))
//                model.setImage(job.optString("price"))

                val model = ItemsModel(
                        job.optString("_id"),
                        job.optString("name"),
                        job.optString("image"),
                        job.optString("category"),
                        job.optString("price")
                )

                dma.add(model)
                itemsArrayList!!.add(model)
            }
            itemsArrayListBuffer = itemsArrayList
            val myAdapter = itemsArrayListBuffer?.let { MyAdapter(this@ListPromosActivity, it) }
            mRecyclerView!!.adapter = myAdapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

//    private fun getKarmaGroupsApiRequest() {
//        val response = AsyncHttpResponse(this, false)
//        val params = RequestParams()
//        response.getAsyncHttp(RestApis.KarmaGroups.vacapediaNews, params)
//    }
//
//    @Throws(JSONException::class)
//    fun onAsyncHttpResponseGet(response: String, url: String) {
//        Log.d("TAG", "onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//        if (url == RestApis.KarmaGroups.vacapediaNews) {
//            Log.d("TAG", "x onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//            dataDestinations = JSONArray(response)
//            processData()
//        }
//    }

}

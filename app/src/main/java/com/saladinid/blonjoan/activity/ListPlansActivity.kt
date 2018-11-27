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
import com.bumptech.glide.Glide
import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.data.GroceriesModel
import com.saladinid.blonjoan.handler.PlansLineAdapter
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class ListPlansActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null
    private var ivImage: ImageView? = null
    private var tvDescription: TextView? = null
    private var dataDestinations: JSONArray? = null
    private var destinationsArrayListBuffer: List<GroceriesModel>? = null
    private var destinationsArrayList: ArrayList<GroceriesModel>? = null
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

        val service = ServiceVolley()
        val apiController = APIController(service)
        val path: String = "http://familygroceries.herokuapp.com/groceries"
        apiController.get(path) { response ->
            // Parse the result
            Log.d("TAG", response.toString())
            dataDestinations = JSONArray(response)
            processData()
        }

    }

    private fun processData() {
        try {
            val dataJson = dataDestinations
            destinationsArrayListBuffer = ArrayList()
            destinationsArrayList = ArrayList()
            mRecyclerView = findViewById(R.id.recyclerview)
            val mLinearLayoutManager = LinearLayoutManager(this@ListPlansActivity)
            mRecyclerView!!.layoutManager = mLinearLayoutManager
            destinationsArrayList!!.clear()
            val dma = ArrayList<GroceriesModel>()
            dma.clear()
            for (j in 0 until dataJson!!.length()) {
                val job = dataJson.getJSONObject(j)
                val model = GroceriesModel()
                model._id = job.optString("_id")
                model.title = job.optString("title")
                val jdes = JSONArray(job.optString("items"))
                model.items = jdes
                dma.add(model)
                destinationsArrayList!!.add(model)
            }
            destinationsArrayListBuffer = destinationsArrayList
            val myAdapter = PlansLineAdapter(this, destinationsArrayListBuffer);
//            val myAdapter = destinationsArrayListBuffer?.let { PlansLineAdapter(this@ListPlansActivity, it) }
            mRecyclerView!!.adapter = myAdapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

//    private fun getKarmaGroupsApiRequest() {
//        val response = AsyncHttpResponse(this, false)
//        val params = RequestParams()
//        response.getAsyncHttp(RestApis.KarmaGroups.vacapediaPlans, params)
//    }
//
//    @Throws(JSONException::class)
//    fun onAsyncHttpResponseGet(response: String, url: String) {
//        Log.d("TAG", "onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//        if (url == RestApis.KarmaGroups.vacapediaPlans) {
//            Log.d("TAG", "x onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//            dataDestinations = JSONArray(response)
//            processData()
//        }
//    }

}
package com.saladinid.blonjoan.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

//import kotlinx.android.synthetic.main.<layout>.*

import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.handler.ListAdapter
//import com.codingdemos.vacapedia.data.DestinationsModel
//import com.codingdemos.vacapedia.handlers.DestinationsLineAdapter
//import com.codingdemos.vacapedia.handlers.ListAdapter
//import com.codingdemos.vacapedia.rest.AsyncHttpResponse
//import com.codingdemos.vacapedia.rest.RestApis
//import com.loopj.android.http.RequestParams

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import android.R.style.Theme_Material_Light_Dialog_Alert
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.saladinid.blonjoan.data.ItemsModel
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
//import com.codingdemos.vacapedia.data.ItemsModel
//import com.saladinid.blonjoan.rest.AsyncHttpResponse
//import com.saladinid.blonjoan.rest.RestApis
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_plan_edit.*

public class AddPlanActivity: AppCompatActivity(), View.OnClickListener {

    internal
    var mToolbar: Toolbar ? = null
    private
    var title: EditText ? = null
    private
    var body_copy: EditText ? = null
    private
    var content: EditText ? = null
    private
    var target_date: EditText ? = null
    private
    var target_time: EditText ? = null
    private
    var costs: EditText ? = null
    private
    var destinations: EditText ? = null
    private
    var alertDialogBuilder: AlertDialog.Builder ? = null
    private
    var alertDialog: AlertDialog ? = null
    private val desPlan: JSONArray ? = null
    private
    var dataDestinations: JSONArray ? = null
    private
    var itemsArrayListBuffer: ArrayList < ItemsModel > ? = null
    private
    var itemsArrayList: ArrayList < ItemsModel > ? = null
    private val imageUrl: String ? = null
    private
    var mRecyclerView: RecyclerView ? = null
    private
    var bn_find_a_restaurant_rl: RelativeLayout ? = null
    private val restaurantName = ""
    private
    var parentLinearLayout: LinearLayout ? = null
    private
    var costList: JSONArray ? = null

    private val onItemClickListener = AdapterView.OnItemClickListener {
        arg0,
        arg1,
        position,
        arg3 ->
        // TODO Auto-generated method stub
    }

    private fun getIntentData() {
        val intent = this.intent
    }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_add)
        val linkTrang = "http://familygroceries.herokuapp.com/items"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
                Response.Listener < String > {
                    response ->
                    Log.d("TAG", response.toString())
                    dataDestinations = JSONArray(response)
                    Log.d("dataDestinations", dataDestinations.toString())
                },
                Response.ErrorListener {}) {
            override fun getHeaders(): MutableMap < String, String > {
                val headers = HashMap < String, String > ()
                return headers
            }
        }
        queue.add(stringRequest)
        initUI()
    }

    @SuppressLint("LongLogTag", "SimpleDateFormat")
    private fun initUI() {
        getIntentData()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dd_booking_form_tv = findViewById(R.id.dd_booking_form_tv) as TextView
        dd_booking_form_tv.setOnClickListener(this)
        title = findViewById(R.id.title) as EditText
        destinations = findViewById(R.id.destinations) as EditText
        bn_find_a_restaurant_rl = findViewById(R.id.bn_find_a_restaurant_rl) as RelativeLayout
        bn_find_a_restaurant_rl!!.setOnClickListener(this)
    }

    /*
     * AlertDialog for Validation Form
     */
    private fun alertWithOk(context: Context, message: String) {
        Log.d(TAG, "alertWithOk() called with:  message = [$message]")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = AlertDialog.Builder(context, Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(context)
        }
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(context.resources.getString(R.string.ok)) {
            dialog,
            id -> dialog.cancel()
        }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        alertDialog = alertDialogBuilder!!.create()
        alertDialog!!.show()
    }

    @SuppressLint("LongLogTag")
    private fun bookValidations() {
        afterSuccess()
        alertForSuccessfulBookingEnquiry("Thank you, your submission has been sent.")
    }

    @SuppressLint("LongLogTag")
    private fun afterSuccess() {
        postBookingRequestJSONApiRequest()
    }

    @SuppressLint("LongLogTag")
    private fun postBookingRequestJSONApiRequest() {
        var jobjContactDetails: JSONObject ? = null
        val dest = JSONArray()
        val animalsArray = destinations!!.text.toString().trim {
            it <= ' '
        }.split("\\s*,\\s*".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()
        if (animalsArray.size > 1) {
            for (i in animalsArray.indices) {
                dest.put(animalsArray[i])
            }
        } else if (animalsArray.size == 1) {
            dest.put(destinations!!.text.toString().trim {
                it <= ' '
            })
        }
        try {
            jobjContactDetails = JSONObject()
            jobjContactDetails.put("title", title!!.text.toString().trim {
                it <= ' '
            })
            jobjContactDetails.put("items", dest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = AlertDialog.Builder(this, Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        val finalJobjContactDetails = jobjContactDetails
        Log.d(TAG, "finalJobjContactDetails: " + finalJobjContactDetails!!)
        val service = ServiceVolley()
        val apiController = APIController(service)
        val path: String = "http://familygroceries.herokuapp.com/groceries"
        apiController.post(path, finalJobjContactDetails) {
            response ->
        }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        alertDialog = alertDialogBuilder!!.create()
        alertDialog!!.show()
    }

    @SuppressLint("LongLogTag")
    private fun alertForSuccessfulBookingEnquiry(message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = AlertDialog.Builder(this, Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(resources.getString(R.string.ok)) {
            dialog,
            id ->
            dialog.cancel()
            val `in` = Intent(this@AddPlanActivity, MainActivity::class.java)
            this@AddPlanActivity.startActivity(`in`)
            this@AddPlanActivity.finish()
        }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        alertDialog = alertDialogBuilder!!.create()
        alertDialog!!.show()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.dd_booking_form_tv -> {
                bookValidations()
            }
            R.id.bn_find_a_restaurant_rl -> {
                hideKeyboard()
                displayPopupWindow(bn_find_a_restaurant_rl)
            }
        }
    }

    private fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    private fun displayPopupWindow(v: View ? ) {
        var popupwindow_obj: PopupWindow ? = null
        try {
            popupwindow_obj = popupDisplay()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        popupwindow_obj?.showAsDropDown(v, -40, 0, Gravity.CENTER_HORIZONTAL)
    }

    @Throws(JSONException::class)
    private fun popupDisplay(): PopupWindow {
        val popupWindow = PopupWindow(this)
        val display = this.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        val dpValue = 40 // margin in dips
        val d = this.resources.displayMetrics.density
        val margin = (dpValue * d).toInt()
        // inflate your layout or dynamically add view
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View ? = null
        if (inflater != null) {
            view = inflater.inflate(R.layout.layout_restaurant_dropdown, null)
        }
        val jsonArray = dataDestinations
        val listItems = getArrayListFromJSONArray(jsonArray)
        val listV = view!!.findViewById(R.id.listv) as ListView
        val adapter = ListAdapter(this, R.layout.list_layout, R.id.karma_resorts_item, listItems)
        listV.adapter = adapter
        listV.onItemClickListener = AdapterView.OnItemClickListener {
            parent,
            view,
            position,
            id ->
            try {
                val obj = JSONObject(listV.getItemAtPosition(position).toString())
                val selectedName = obj.getString("name")
                val selectedId = obj.getString("_id")
                bn_find_a_restaurant_tv_add!!.text = selectedName
                nowDestinationsSelected = selectedId
                destinations!!.setText(nowDestinationsSelected)
                popupWindow.dismiss()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        popupWindow.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.MATCH_PARENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = view
        return popupWindow
    }

    private fun getArrayListFromJSONArray(jsonArray: JSONArray ? ): ArrayList < JSONObject > {
        val aList = ArrayList < JSONObject > ()
        try {
            if (jsonArray != null) {
                for (i in 0 until jsonArray.length()) {
                    aList.add(jsonArray.getJSONObject(i))
                }
            }
        } catch (je: JSONException) {
            je.printStackTrace()
        }
        return aList
    }

    override fun onResume() {
        super.onResume()
    }

    private fun processData() {
        try {
            val dataJson = desPlan
            itemsArrayListBuffer = ArrayList < ItemsModel > ()
            itemsArrayList = ArrayList < ItemsModel > ()
            mRecyclerView = findViewById(R.id.recyclerview)
            val mLinearLayoutManager = LinearLayoutManager(this@AddPlanActivity)
            mRecyclerView!!.layoutManager = mLinearLayoutManager
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
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private val TAG = "AddPlanActivity"
        private
        var bn_find_a_restaurant_tv_add: TextView ? = null
        private
        var nowDestinationsSelected = ""
    }

}
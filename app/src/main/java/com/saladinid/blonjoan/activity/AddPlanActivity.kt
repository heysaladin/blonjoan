package com.codingdemos.vacapedia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
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
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

//import kotlinx.android.synthetic.main.<layout>.*

import com.saladinid.blonjoan.R
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
import com.codingdemos.vacapedia.data.DestinationsModel
import com.saladinid.blonjoan.activity.MainActivity
//import com.saladinid.blonjoan.rest.AsyncHttpResponse
//import com.saladinid.blonjoan.rest.RestApis
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_plan_edit.*

public class AddPlanActivity : AppCompatActivity(), View.OnClickListener
//        ,
//        AsyncHttpResponse.AsyncHttpResponseListener
{
    internal var mToolbar: Toolbar? = null
    private var title: EditText? = null
    private var body_copy: EditText? = null
    private var content: EditText? = null
    private var target_date: EditText? = null
    private var target_time: EditText? = null
    private var costs: EditText? = null
    private var destinations: EditText? = null
    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null
    private val desPlan: JSONArray? = null
    private var dataDestinations: JSONArray? = null
    private var destinationsArrayListBuffer: ArrayList<DestinationsModel>? = null
    private var destinationsArrayList: ArrayList<DestinationsModel>? = null
    private val imageUrl: String? = null
    private var mRecyclerView: RecyclerView? = null
    private var bn_find_a_restaurant_rl: RelativeLayout? = null
    private val restaurantName = ""
    private var parentLinearLayout: LinearLayout? = null
    private var costList: JSONArray? = null

    private val onItemClickListener = AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
        // TODO Auto-generated method stub
    }

    private fun getIntentData() {
        val intent = this.intent
    }

//    fun onAddField(v: View) {
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val rowView = inflater.inflate(R.layout.field, null)
//        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
//        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
//
//    }
//
//    fun onDelete(v: View) {
//        parentLinearLayout!!.removeView(v.parent as View)
//        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_add)
        parentLinearLayout = findViewById(R.id.parent_linear_layout) as LinearLayout
//        mToolbar = findViewById(R.id.toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
//        toolbar.setNavigationOnClickListener { onBackPressed() }
//        toolbar.title = "Note"
        initUI()
    }

    @SuppressLint("LongLogTag", "SimpleDateFormat")
    private fun initUI() {
        getIntentData()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dd_booking_form_tv = findViewById(R.id.dd_booking_form_tv) as TextView
        dd_booking_form_tv.setOnClickListener(this)
        title = findViewById(R.id.title) as EditText
        body_copy = findViewById(R.id.body_copy) as EditText
        content = findViewById(R.id.content) as EditText
        target_date = findViewById(R.id.target_date) as EditText
        costs = findViewById(R.id.costs) as EditText
        target_time = findViewById(R.id.target_time) as EditText
        destinations = findViewById(R.id.destinations) as EditText
        //bn_find_a_restaurant_rl = findViewById(R.id.bn_find_a_restaurant_rl) as RelativeLayout
//        bn_find_a_restaurant_rl = findViewById(R.id.bn_find_a_restaurant_tv)
        bn_find_a_restaurant_rl!!.setOnClickListener(this)
//        bn_find_a_restaurant_tv!!.text = restaurantName
//        getKarmaGroupsApiRequest()
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
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(context.resources.getString(R.string.ok)
        ) { dialog, id -> dialog.cancel() }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        alertDialog = alertDialogBuilder!!.create()
        alertDialog!!.show()
    }

    @SuppressLint("LongLogTag")
    private fun bookValidations() {
//        val responseValidation = AsyncHttpResponse(this, true)
        if (title!!.text == null || title!!.length() == 0) {
            alertWithOk(this, "please provide title!")
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alertDialogBuilder = AlertDialog.Builder(this, Theme_Material_Light_Dialog_Alert)
            } else {
                alertDialogBuilder = AlertDialog.Builder(this)
            }
            afterSuccess()
//            synchronized(responseValidation) {
                alertForSuccessfulBookingEnquiry("Thank you, your submission has been sent.")
//            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun afterSuccess() {
        postBookingRequestJSONApiRequest()
    }

    @SuppressLint("LongLogTag")
    private fun postBookingRequestJSONApiRequest() {
//        val response = AsyncHttpResponse(this, true)
        var jobjContactDetails: JSONObject? = null
        val dest = JSONArray()
        val animalsArray = destinations!!.text.toString().trim { it <= ' ' }.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (animalsArray.size > 1) {
            for (i in animalsArray.indices) {
                dest.put(animalsArray[i])
            }
        } else if (animalsArray.size == 1) {
            dest.put(destinations!!.text.toString().trim { it <= ' ' })
        }
        try {
            jobjContactDetails = JSONObject()
            jobjContactDetails.put("title", title!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("body_copy", body_copy!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("content", content!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("target_date", target_date!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("target_time", target_time!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("costs", costList)
            jobjContactDetails.put("destinations", dest)
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
//        response.postJson(RestApis.KarmaGroups.vacapediaPlans, finalJobjContactDetails)
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
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(resources.getString(R.string.ok)
        ) { dialog, id ->
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
        when (v.id) {
            R.id.dd_booking_form_tv -> {
                costList = JSONArray()
                val parentLong = Integer.parseInt(parentLinearLayout!!.childCount.toString()) - 1
                for (k in 0 until parentLong) {
                    try {
                        val currentView = parentLinearLayout!!.getChildAt(k)
                        //val currentEditName = currentView.findViewById(R.id.text_edit_text)
                        //val currentEditCost = currentView.findViewById(R.id.number_edit_text)
                        if (text_edit_text.getText().toString() != "" || number_edit_text.getText().toString() != "") {
                            val costObj = JSONObject("{" +
                                    "\"name\":\"" + text_edit_text.getText() + "\"," +
                                    "\"cost\":\"" + number_edit_text.getText() + "\"" +
                                    "}")
                            // Log.d(TAG, k + " k >>>>>>>> : " + costObj);
                            costList!!.put(costObj)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
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

    private fun displayPopupWindow(v: View?) {
        var popupwindow_obj: PopupWindow? = null
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
        var view: View? = null
        if (inflater != null) {
            view = inflater.inflate(R.layout.layout_restaurant_dropdown, null)
        }
        val jsonArray = dataDestinations
        val listItems = getArrayListFromJSONArray(jsonArray)
        val listV = view!!.findViewById(R.id.listv) as ListView
//        val adapter = ListAdapter(this, R.layout.list_layout, R.id.karma_resorts_item, listItems)
//        listV.adapter = adapter
        listV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            try {
                val obj = JSONObject(listV.getItemAtPosition(position).toString())
                val selectedName = obj.getString("name")
                val selectedId = obj.getString("_id")
//                bn_find_a_restaurant_tv!!.text = selectedName
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

    private fun getArrayListFromJSONArray(jsonArray: JSONArray?): ArrayList<JSONObject> {
        val aList = ArrayList<JSONObject>()
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
            destinationsArrayListBuffer = ArrayList<DestinationsModel>()
            destinationsArrayList = ArrayList<DestinationsModel>()
//            mRecyclerView = findViewById(R.id.recyclerview)
            val mLinearLayoutManager = LinearLayoutManager(this@AddPlanActivity)
            recyclerview!!.layoutManager = mLinearLayoutManager
            destinationsArrayList!!.clear()
            val dma = ArrayList<DestinationsModel>()
            dma.clear()
            for (j in 0 until dataJson!!.length()) {
                val job = dataJson.getJSONObject(j)
                val model = DestinationsModel()
//                model.setMenuID(j.toString())
//                model.setMenuName("nama$j")
//                model.setName(job.optString("name"))
//                model.setPostID(job.optString("id"))
//                model.setImage(job.optString("image"))
//                model.set_id(job.optString("_id"))
//                model.setCategory(job.optString("category"))
//                model.setLocation(job.optString("location"))
//                model.setDescription(job.optString("description"))
//                model.setLatitude(job.optString("latitude"))
//                model.setLongitude(job.optString("longitude"))
//                model.setAddress(job.optString("address"))
//                model.setDistance(job.optString("distance"))
//                model.setNote(job.optString("note"))
//                model.setCosts(job.optString("costs"))
//                model.setTotal_cost(job.optString("total_cost"))
                dma.add(model)
                destinationsArrayList!!.add(model)
            }
            destinationsArrayListBuffer = destinationsArrayList
//            val myAdapter = DestinationsLineAdapter(this@AddPlanActivity, destinationsArrayListBuffer)
//            recyclerview!!.adapter = myAdapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

//    private fun getKarmaGroupsApiRequest() {
//        val response = AsyncHttpResponse(this, false)
////        val params = RequestParams()
////        response.getAsyncHttp(RestApis.KarmaGroups.vacapediaDestinations, params)
//    }

//    @Throws(JSONException::class)
//    override fun onAsyncHttpResponseGet(response: String, url: String) {
//        Log.d("TAG", "onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//        if (url == RestApis.KarmaGroups.vacapediaDestinations) {
//            Log.d("TAG", "x onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//            dataDestinations = JSONArray(response)
//            // processData();
//        }
//    }

    companion object {
        private val TAG = "AddPlanActivity"
        private var bn_find_a_restaurant_tv: TextView? = null
        private var nowDestinationsSelected = ""
    }

}
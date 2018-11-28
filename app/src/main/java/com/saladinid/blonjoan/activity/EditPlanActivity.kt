package com.saladinid.blonjoan.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.data.ItemsModel
import com.saladinid.blonjoan.handler.ListAdapter
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.MutableMap
import kotlin.collections.dropLastWhile
import kotlin.collections.indices
import kotlin.collections.toTypedArray

public class EditPlanActivity: AppCompatActivity(), View.OnClickListener {
    internal
    var mToolbar: Toolbar ? = null
    private
    var title: EditText ? = null
    private
    var destinations: EditText ? = null
    private
    var alertDialogBuilder: android.app.AlertDialog.Builder ? = null
    private
    var alertDialog: android.app.AlertDialog ? = null
    private
    var bn_find_a_restaurant_rl: RelativeLayout ? = null
    private
    var id: String ? = null
    private
    var titleString: String ? = null
    private
    var desPlan: JSONArray ? = null
    private
    var dataDestinations: JSONArray ? = null
    private
    var itemsArrayListBuffer: List < ItemsModel > ? = null
    private
    var itemsArrayList: ArrayList < ItemsModel > ? = null
    private
    var costJsonArray = JSONArray()
    private
    var parentLinearLayout: LinearLayout ? = null

    private fun getIntentData() {
        val intent = this.intent
        id = intent.getStringExtra("_id")
        titleString = intent.getStringExtra("title")
        var jdes: JSONArray ? = null
        try {
            jdes = JSONArray(intent.getStringExtra("items"))
            desPlan = jdes
            Log.d("XXX", "jdes: " + jdes);
            val jPlain = StringBuilder()
            for (z in 0 until jdes.length()) {
                val jdesob = jdes.getJSONObject(z)
                jPlain.append(jdesob.getString("_id"))
                if (z != jdes.length() - 1) {
                    jPlain.append(",")
                }
            }
            Log.d("XXX", "jPlain: " + jPlain);
            destinationsString = jPlain.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "destinationsString = [" + destinationsString + "]");
    }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_edit)

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
        val delete_tv = findViewById(R.id.delete_tv) as TextView
        delete_tv.setOnClickListener(this)
        nowDestinationsSelected = destinationsString
        bn_find_a_restaurant_rl = findViewById(R.id.bn_find_a_restaurant_rl) as RelativeLayout
        bn_find_a_restaurant_rl!!.setOnClickListener(this)
        title = findViewById(R.id.title) as EditText
        destinations = findViewById(R.id.destinations) as EditText
        title!!.setText(titleString)
        destinations!!.setText(nowDestinationsSelected)
    }

    /*
     * AlertDialog for Validation Form
     */
    @SuppressLint("ObsoleteSdkInt")
    private fun alertWithOk(context: Context, message: String) {
        Log.d(TAG, "alertWithOk() called with:  message = [$message]")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = android.app.AlertDialog.Builder(context)
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

    @SuppressLint("LongLogTag", "ObsoleteSdkInt")
    private fun bookValidations() {
        if (title!!.text == null || title!!.length() == 0) {
            alertWithOk(this, "please provide title!")
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
            } else {
                alertDialogBuilder = AlertDialog.Builder(this)
            }
            afterSuccess()
            alertForSuccessfulBookingEnquiry("Thank you, your submission has been sent.")
        }
    }

    @SuppressLint("LongLogTag")
    private fun afterSuccess() {
        postBookingRequestJSONApiRequest()
    }

    @SuppressLint("LongLogTag", "ObsoleteSdkInt")
    private fun postBookingRequestJSONApiRequest() {
        var jobjContactDetails: JSONObject ? = null
        val dest = JSONArray()
        val animalsArray = destinations!!.text.toString().trim {
            it <= ' '
        }.split("\\s*,\\s*".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()
        for (i in animalsArray.indices) {
            dest.put(animalsArray[i])
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
            alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        val finalJobjContactDetails = jobjContactDetails
        Log.d(TAG, "finalJobjContactDetails: " + finalJobjContactDetails!!)
        val service = ServiceVolley()
        val apiController = APIController(service)
        val path: String = "http://familygroceries.herokuapp.com/groceries" + "/" + id
        apiController.put(path, finalJobjContactDetails) {
            response ->
        }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        alertDialog = alertDialogBuilder!!.create()
        alertDialog!!.show()
    }

    @SuppressLint("LongLogTag", "ObsoleteSdkInt")
    private fun alertForSuccessfulBookingEnquiry(message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(resources.getString(R.string.ok)) {
            dialog,
            id ->
            dialog.cancel()
            val `in` = Intent(this@EditPlanActivity, MainActivity::class.java)
            this@EditPlanActivity.startActivity(`in`)
            this@EditPlanActivity.finish()
            Toast.makeText(this@EditPlanActivity, "Thank You", Toast.LENGTH_LONG).show()
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
            R.id.delete_tv -> {
                hideKeyboard()
                val service = ServiceVolley()
                val apiController = APIController(service)
                val path: String = "http://familygroceries.herokuapp.com/groceries" + "/" + id
                apiController.delete(path) {
                    response ->
                    // Parse the result
                    Log.d("TAG_HOME", response.toString())
                }
                finish()
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
                nowDestinationsSelected = ""
                if (!destinationsString.equals("")) {
                    nowDestinationsSelected = "$destinationsString, $selectedId"
                } else {
                    nowDestinationsSelected = "$selectedId"
                }
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
        private val TAG = "EditPlanActivity"
        private
        var bn_find_a_restaurant_tv_edit: TextView ? = null
        private
        var destinationsString: String ? = null
        private
        var nowDestinationsSelected: String ? = ""
    }

}
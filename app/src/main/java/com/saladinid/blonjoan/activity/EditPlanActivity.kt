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
import android.support.v7.widget.LinearLayoutManager
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
//import com.codingdemos.vacapedia.data.ItemsModel

import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.data.ItemsModel
import com.saladinid.blonjoan.handler.MyAdapter
import com.saladinid.blonjoan.handler.ListAdapter
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
//import com.saladinid.blonjoan.rest.AsyncHttpResponse
//import com.saladinid.blonjoan.rest.RestApis
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_plan_edit.*
//import com.codingdemos.vacapedia.data.CostsModel
//import com.codingdemos.vacapedia.data.DestinationsModel
//import com.codingdemos.vacapedia.handlers.ListAdapter
//import com.codingdemos.vacapedia.handlers.DestinationsLineAdapter
//import com.codingdemos.vacapedia.handlers.SliderAdapter
//import com.codingdemos.vacapedia.rest.AsyncHttpResponse
//import com.codingdemos.vacapedia.rest.RestApis
//import com.loopj.android.http.RequestParams

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

public class EditPlanActivity : AppCompatActivity(), View.OnClickListener
//        , AsyncHttpResponse.AsyncHttpResponseListener
{
    internal var mToolbar: Toolbar? = null
    private var title: EditText? = null
    private var body_copy: EditText? = null
    private var content: EditText? = null
    private var target_date: EditText? = null
    private var target_time: EditText? = null
    private var costs: EditText? = null
    private var destinations: EditText? = null
    private var alertDialogBuilder: android.app.AlertDialog.Builder? = null
    private var alertDialog: android.app.AlertDialog? = null
    private var bn_find_a_restaurant_rl: RelativeLayout? = null
    private var id: String? = null
    private var titleString: String? = null
    private var body_copyString: String? = null
    private var contentString: String? = null
    private var target_dateString: String? = null
    private var target_timeString: String? = null
    private var costsString: String? = null
    private var desPlan: JSONArray? = null
    private var dataDestinations: JSONArray? = null
    private var itemsArrayListBuffer: List<ItemsModel>? = null
    private var itemsArrayList: ArrayList<ItemsModel>? = null
    private var mRecyclerView: RecyclerView? = null
    private val restaurantName = ""
    private var costJsonArray = JSONArray()
//    private var destinationsArrayListc: ArrayList<CostsModel>? = null
    private var parentLinearLayout: LinearLayout? = null
    private var costList: JSONArray? = null

    private val onItemClickListener = AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
        // TODO Auto-generated method stub
    }

    private fun getIntentData() {
        val intent = this.intent
        id = intent.getStringExtra("_id")
        titleString = intent.getStringExtra("title")
//        body_copyString = intent.getStringExtra("body_copy")
//        contentString = intent.getStringExtra("content")
//        target_dateString = intent.getStringExtra("target_date")
//        target_timeString = intent.getStringExtra("target_time")
//        costsString = intent.getStringExtra("costs")
        var jdes: JSONArray? = null
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

        // Log.d(TAG, "target_dateString = [" + target_dateString + "]");
        // Log.d(TAG, "target_timeString = [" + target_timeString + "]");
         Log.d(TAG, "destinationsString = [" + destinationsString + "]");
        // Log.d(TAG, "costsString = [" + costsString + "]");
//        var constList = JSONArray()
//        try {
//            constList = JSONArray(costsString)
////            destinationsArrayListc = ArrayList<CostsModel>()
////            destinationsArrayListc!!.clear()
//            for (j in 0 until constList.length()) {
//                val constItem = constList.getJSONObject(j)
////                val modelc = CostsModel()
////                modelc.set_id(constItem.optString("_id"))
////                modelc.setName(constItem.optString("name"))
////                modelc.setCost(constItem.optString("cost"))
////                destinationsArrayListc!!.add(modelc)
//            }
//            costJsonArray = constList
//            costsString = costJsonArray.toString()
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }

    }

    fun onAddFieldFill(name: String, cost: String) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.field, null)
//        val currentName = rowView.findViewById(R.id.text_edit_text)
//        val currentCost = rowView.findViewById(R.id.number_edit_text)
//        currentName.setText(name)
//        currentCost.setText(cost)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
    }

    fun onAddField(v: View) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.field, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
    }

    fun onDelete(v: View) {
        parentLinearLayout!!.removeView(v.parent as View)
        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_edit)
        //mToolbar = findViewById(R.id.toolbar)
//        parentLinearLayout = findViewById(R.id.parent_linear_layout) as LinearLayout
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
//        toolbar.setNavigationOnClickListener { onBackPressed() }
//        toolbar.title = "Note"


        val linkTrang = "http://familygroceries.herokuapp.com/items"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
                Response.Listener<String> { response ->
                    //                    Log.d("A", "Response is: " + response.substring(0,500))

                    Log.d("TAG", response.toString())
                    dataDestinations = JSONArray(response)
                    Log.d("dataDestinations", dataDestinations.toString())
                    //processData()

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



        initUI()
    }

    @SuppressLint("LongLogTag", "SimpleDateFormat")
    private fun initUI() {
        getIntentData()
        try {
            for (j in 0 until costJsonArray.length()) {
                val costItem = costJsonArray.getJSONObject(j)
                onAddFieldFill(costItem.getString("name"), costItem.getString("cost"))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dd_booking_form_tv = findViewById(R.id.dd_booking_form_tv) as TextView
        dd_booking_form_tv.setOnClickListener(this)
        val delete_tv = findViewById(R.id.delete_tv) as TextView
        delete_tv.setOnClickListener(this)
        nowDestinationsSelected = destinationsString
        bn_find_a_restaurant_rl = findViewById(R.id.bn_find_a_restaurant_rl) as RelativeLayout
//        bn_find_a_restaurant_tv = findViewById(R.id.bn_find_a_restaurant_tv)
        bn_find_a_restaurant_rl!!.setOnClickListener(this)
        title = findViewById(R.id.title) as EditText
//        body_copy = findViewById(R.id.body_copy) as EditText
//        content = findViewById(R.id.content) as EditText
//        target_date = findViewById(R.id.target_date) as EditText
//        target_time = findViewById(R.id.target_time) as EditText
//        costs = findViewById(R.id.costs) as EditText
        destinations = findViewById(R.id.destinations) as EditText
//        items = findViewById(R.id.items) as EditText
//        bn_find_a_restaurant_tv!!.text = restaurantName
        title!!.setText(titleString)
//        body_copy!!.setText(body_copyString)
//        content!!.setText(contentString)
//        target_date!!.setText(target_dateString)
//        target_time!!.setText(target_timeString)
        destinations!!.setText(nowDestinationsSelected)
//        getKarmaGroupsApiRequest()
    }

    /*
     * AlertDialog for Validation Form
     */
    private fun alertWithOk(context: Context, message: String) {
        Log.d(TAG, "alertWithOk() called with:  message = [$message]")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = android.app.AlertDialog.Builder(context)
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
                alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
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
        Log.d(TAG, " destinations >>>>>>>> : " + destinations);
        val animalsArray = destinations!!.text.toString().trim { it <= ' ' }.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in animalsArray.indices) {
            dest.put(animalsArray[i])
        }
        try {
            jobjContactDetails = JSONObject()
            jobjContactDetails.put("title", title!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("body_copy", body_copy!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("content", content!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("target_date", target_date!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("target_time", target_time!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("costs", costList)
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
//        response.putJson(RestApis.KarmaGroups.vacapediaPlans + "/" + id, finalJobjContactDetails)


        val service = ServiceVolley()
        val apiController = APIController(service)
        val path: String = "http://familygroceries.herokuapp.com/groceries"+"/"+id
//        val params = JSONObject()
//            params.put("title", "belanja bulanan desember")
//            params.put("items", null)
        apiController.put(path, finalJobjContactDetails) { response -> }

        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        alertDialog = alertDialogBuilder!!.create()
        alertDialog!!.show()
    }

    @SuppressLint("LongLogTag")
    private fun alertForSuccessfulBookingEnquiry(message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(resources.getString(R.string.ok)
        ) { dialog, id ->
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
        when (v.id) {
            R.id.dd_booking_form_tv -> {
//                costList = JSONArray()
//                val parentLong = Integer.parseInt(parentLinearLayout!!.childCount.toString()) - 1
//                for (k in 0 until parentLong) {
//                    try {
//                        val currentView = parentLinearLayout!!.getChildAt(k)
//                        //val currentEditName = currentView.findViewById(R.id.text_edit_text)
//                        //val currentEditCost = currentView.findViewById(R.id.number_edit_text)
//                        if (text_edit_text.getText().toString() != "" || number_edit_text.getText().toString() != "") {
//                            val costObj = JSONObject("{" +
//                                    "\"name\":\"" + text_edit_text.getText() + "\"," +
//                                    "\"cost\":\"" + number_edit_text.getText() + "\"" +
//                                    "}")
//                            // Log.d(TAG, k + " k >>>>>>>> : " + costObj);
//                            costList!!.put(costObj)
//                        }
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//
//                }
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
//                val path: String = "http://familygroceries.herokuapp.com/items"


            apiController.delete(path) { response ->
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
        val adapter = ListAdapter(this, R.layout.list_layout, R.id.karma_resorts_item, listItems)
        listV.adapter = adapter
        listV.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            try {
                val obj = JSONObject(listV.getItemAtPosition(position).toString())
                val selectedName = obj.getString("name")
                val selectedId = obj.getString("_id")
//                bn_find_a_restaurant_tv_edit!!.text = selectedName
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
            itemsArrayListBuffer = ArrayList<ItemsModel>()
            itemsArrayList = ArrayList<ItemsModel>()
//            mRecyclerView = findViewById(R.id.recyclerview)
//            val mLinearLayoutManager = LinearLayoutManager(this@EditPlanActivity)
//            mRecyclerView!!.layoutManager = mLinearLayoutManager
            itemsArrayList!!.clear()
            val dma = ArrayList<ItemsModel>()
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
                itemsArrayList!!.add(model)
            }
            itemsArrayListBuffer = itemsArrayList
//            val myAdapter = MyAdapter(this@EditPlanActivity, itemsArrayListBuffer)
//            mRecyclerView!!.adapter = myAdapter

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
//            processData()
//        }
//    }

    companion object {
        private val TAG = "EditPlanActivity"
        private var bn_find_a_restaurant_tv_edit: TextView? = null
        private var destinationsString: String? = null
        private var nowDestinationsSelected: String? = ""
    }

}
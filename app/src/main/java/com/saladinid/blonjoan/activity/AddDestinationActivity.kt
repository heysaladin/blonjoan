package com.saladinid.blonjoan.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import com.saladinid.blonjoan.R
//import com.codingdemos.vacapedia.rest.AsyncHttpResponse
//import com.codingdemos.vacapedia.rest.RestApis

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import android.R.style.Theme_Material_Light_Dialog_Alert
import com.saladinid.blonjoan.R.id.toolbar
//import com.saladinid.blonjoan.rest.AsyncHttpResponse
//import com.saladinid.blonjoan.rest.RestApis
import kotlinx.android.synthetic.main.activity_destination_add.*

public class AddDestinationActivity : AppCompatActivity(), View.OnClickListener
//        , AsyncHttpResponse.AsyncHttpResponseListener
{
    internal var mToolbar: Toolbar? = null
    private var name: EditText? = null
    private var image: EditText? = null
    private var category: EditText? = null
    private var location: EditText? = null
    private var description: EditText? = null
    private var latitude: EditText? = null
    private var longitude: EditText? = null
    private var address: EditText? = null
    private var distance: EditText? = null
    private var note: EditText? = null
    private var costs: EditText? = null
    private var total_cost: EditText? = null
    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null
    private var parentLinearLayout: LinearLayout? = null
    private var costList: JSONArray? = null
    private fun getIntentData() {
        val intent = this.intent
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
        setContentView(R.layout.activity_destination_add)
        parentLinearLayout = findViewById(R.id.parent_linear_layout) as LinearLayout
        // mToolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.title = "Note"
        initUI()
    }

    @SuppressLint("LongLogTag", "SimpleDateFormat", "NewApi")
    private fun initUI() {
        getIntentData()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dd_booking_form_tv = findViewById(R.id.dd_booking_form_tv) as TextView
        dd_booking_form_tv.setOnClickListener(this)
        name = findViewById(R.id.name) as EditText
        image = findViewById(R.id.image) as EditText
        category = findViewById(R.id.category) as EditText
        location = findViewById(R.id.location) as EditText
        description = findViewById(R.id.description) as EditText
        latitude = findViewById(R.id.latitude) as EditText
        longitude = findViewById(R.id.longitude) as EditText
        address = findViewById(R.id.address) as EditText
        distance = findViewById(R.id.distance) as EditText
        note = findViewById(R.id.note) as EditText
        costs = findViewById(R.id.costs) as EditText
        total_cost = findViewById(R.id.total_cost) as EditText
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
        if (name!!.text == null || name!!.length() == 0) {
            alertWithOk(this, "please provide name!")
        } else if (image!!.text == null || image!!.length() == 0) {
            alertWithOk(this, "please provide note image!")
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
        val jarr = JSONArray()
        try {
            jobjContactDetails = JSONObject()
            jobjContactDetails.put("name", name!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("image", image!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("category", category!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("location", location!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("description", description!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("latitude", latitude!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("longitude", longitude!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("address", address!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("distance", distance!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("note", note!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("costs", costList)
            jobjContactDetails.put("total_cost", total_cost!!.text.toString().trim { it <= ' ' })
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
//        response.postJson(RestApis.KarmaGroups.vacapediaDestinations, finalJobjContactDetails)
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
            val `in` = Intent(this@AddDestinationActivity, MainActivity::class.java)
            this@AddDestinationActivity.startActivity(`in`)
            this@AddDestinationActivity.finish()
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
                        // val currentEditName = currentView.findViewById(R.id.text_edit_text)
                        // val currentEditCost = currentView.findViewById(R.id.number_edit_text)
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
        }
    }

//    @SuppressLint("LongLogTag")
//    @Throws(JSONException::class)
//    override fun onAsyncHttpResponseGet(response: String, url: String) {
//        Log.d(TAG, "onAsyncHttpResponseGet() called with: response = [$response], url = [$url]")
//    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private val TAG = "AddDestinationActivity"
    }

}

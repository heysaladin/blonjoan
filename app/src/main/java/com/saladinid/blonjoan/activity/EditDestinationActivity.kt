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
import android.widget.Toast

import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
//import com.saladinid.blonjoan.rest.AsyncHttpResponse
//import com.saladinid.blonjoan.rest.RestApis
import kotlinx.android.synthetic.main.activity_destination_add.*
import kotlinx.android.synthetic.main.activity_destination_add.view.*
//import com.codingdemos.vacapedia.data.CostsModel
//import com.codingdemos.vacapedia.data.DestinationsModel
//import com.codingdemos.vacapedia.handlers.CostsLineAdapter
//import com.codingdemos.vacapedia.handlers.DestinationsLineAdapter
//import com.codingdemos.vacapedia.handlers.SliderAdapter
//import com.codingdemos.vacapedia.rest.AsyncHttpResponse
//import com.codingdemos.vacapedia.rest.RestApis
//import com.google.android.gms.common.api.Status
//import com.google.android.gms.location.places.Place
//import com.google.android.gms.location.places.ui.PlaceAutocomplete
//import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
//import com.google.android.gms.location.places.ui.PlaceSelectionListener
//import com.google.android.gms.maps.model.LatLng
//import com.loopj.android.http.RequestParams

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

public class EditDestinationActivity: AppCompatActivity(), View.OnClickListener {

    private
    var mToolbar: Toolbar ? = null
    private
    var name: EditText ? = null
    private
    var image: EditText ? = null
    private
    var category: EditText ? = null
    private
    var unit: EditText ? = null
    private
    var price: EditText ? = null
    private
    var location: EditText ? = null
    private
    var description: EditText ? = null
    private
    var latitude: EditText ? = null
    private
    var longitude: EditText ? = null
    private
    var address: EditText ? = null
    private
    var distance: EditText ? = null
    private
    var note: EditText ? = null
    private
    var costs: EditText ? = null
    private
    var total_cost: EditText ? = null
    private
    var alertDialogBuilder: AlertDialog.Builder ? = null
    private
    var alertDialog: AlertDialog ? = null
    private val noteID: String ? = null
    private
    var id: String ? = null
    private
    var nameString: String ? = null
    private
    var imageString: String ? = null
    private
    var categoryString: String ? = null
    private
    var unitString: String ? = null
    private
    var priceString: String ? = null
    private
    var locationString: String ? = null
    private
    var descriptionString: String ? = null
    private
    var latitudeString: String ? = null
    private
    var longitudeString: String ? = null
    private
    var addressString: String ? = null
    private
    var distanceString: String ? = null
    private
    var noteString: String ? = null
    private
    var costsString: String ? = null
    private
    var total_costString: String ? = null
    private
    var parentLinearLayout: LinearLayout ? = null
    private
    var costList: JSONArray ? = null
    private
    var costJsonArray = JSONArray()

    private fun getIntentData() {
        val intent = this.intent
        id = intent.getStringExtra("_id")
        nameString = intent.getStringExtra("name")
        imageString = intent.getStringExtra("image")
        categoryString = intent.getStringExtra("category")
        unitString = intent.getStringExtra("unit")
        priceString = intent.getStringExtra("price")
    }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_edit)
        mToolbar = findViewById(R.id.toolbar)
        mToolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        mToolbar!!.setNavigationOnClickListener {
            onBackPressed()
        }
        mToolbar!!.title = "Edit Destination"
        initUI()
    }

    private fun initUI() {
        getIntentData()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val dd_booking_form_tv = findViewById(R.id.dd_booking_form_tv) as TextView
        dd_booking_form_tv.setOnClickListener(this)
        val delete_tv = findViewById(R.id.delete_tv) as TextView
        delete_tv.setOnClickListener(this)
        name = findViewById(R.id.name) as EditText
        image = findViewById(R.id.image) as EditText
        category = findViewById(R.id.category) as EditText
        unit = findViewById(R.id.unit) as EditText
        price = findViewById(R.id.price) as EditText
        name!!.setText(nameString)
        image!!.setText(imageString)
        category!!.setText(categoryString)
        unit!!.setText(unitString)
        price!!.setText(priceString)
    }

    /*
     * AlertDialog for Validation Form
     */
    private fun alertWithOk(context: Context, message: String) {
        Log.d(TAG, "alertWithOk() called with:  message = [$message]")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
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
        if (name!!.text == null || name!!.length() == 0) {
            alertWithOk(this, "please provide name!")
        } else if (image!!.text == null || image!!.length() == 0) {
            alertWithOk(this, "please provide note image!")
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

    @SuppressLint("LongLogTag")
    private fun postBookingRequestJSONApiRequest() {
        var jobjContactDetails: JSONObject ? = null
        try {
            jobjContactDetails = JSONObject()
            jobjContactDetails.put("name", name!!.text.toString().trim {
                it <= ' '
            })
            jobjContactDetails.put("image", image!!.text.toString().trim {
                it <= ' '
            })
            jobjContactDetails.put("category", category!!.text.toString().trim {
                it <= ' '
            })
            jobjContactDetails.put("unit", unit!!.text.toString().trim {
                it <= ' '
            })
            jobjContactDetails.put("price", price!!.text.toString().trim {
                it <= ' '
            })
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
        val path: String = "http://familygroceries.herokuapp.com/items" + "/" + id
        apiController.put(path, finalJobjContactDetails) {
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
            alertDialogBuilder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        alertDialogBuilder!!.setMessage(message).setCancelable(false).setPositiveButton(resources.getString(R.string.ok)) {
            dialog,
            id ->
            dialog.cancel()
            val `in` = Intent(this@EditDestinationActivity, MainActivity::class.java)
            this@EditDestinationActivity.startActivity(`in`)
            this@EditDestinationActivity.finish()
            Toast.makeText(this@EditDestinationActivity, "Thank You", Toast.LENGTH_LONG).show()
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
            R.id.delete_tv -> {
                val service = ServiceVolley()
                val apiController = APIController(service)
                val path: String = "http://familygroceries.herokuapp.com/items" + "/" + id
                apiController.delete(path) {
                    response ->
                    // Parse the result
                    Log.d("TAG_HOME", response.toString())
                }
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private val TAG = "EditDestinationActivity"
    }

}
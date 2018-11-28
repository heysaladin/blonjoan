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

public class EditDestinationActivity : AppCompatActivity(), View.OnClickListener
//        , AsyncHttpResponse.AsyncHttpResponseListener
{
    private var mToolbar: Toolbar? = null
    private var name: EditText? = null
    private var image: EditText? = null
    private var category: EditText? = null
    private var unit: EditText? = null
    private var price: EditText? = null
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
    private val noteID: String? = null
    private var id: String? = null
    private var nameString: String? = null
    private var imageString: String? = null
    private var categoryString: String? = null
    private var unitString: String? = null
    private var priceString: String? = null
    private var locationString: String? = null
    private var descriptionString: String? = null
    private var latitudeString: String? = null
    private var longitudeString: String? = null
    private var addressString: String? = null
    private var distanceString: String? = null
    private var noteString: String? = null
    private var costsString: String? = null
    private var total_costString: String? = null
    private var parentLinearLayout: LinearLayout? = null
    private var costList: JSONArray? = null
//    private var destinationsArrayList: ArrayList<CostsModel>? = null
    private var costJsonArray = JSONArray()

    private fun getIntentData() {
        val intent = this.intent
        id = intent.getStringExtra("_id")
        nameString = intent.getStringExtra("name")
        imageString = intent.getStringExtra("image")
        categoryString = intent.getStringExtra("category")
        unitString = intent.getStringExtra("unit")
        priceString = intent.getStringExtra("price")
//        locationString = intent.getStringExtra("location")
//        descriptionString = intent.getStringExtra("description")
//        latitudeString = intent.getStringExtra("latitude")
//        longitudeString = intent.getStringExtra("longitude")
//        addressString = intent.getStringExtra("address")
//        distanceString = intent.getStringExtra("distance")
//        noteString = intent.getStringExtra("note")
//        costsString = intent.getStringExtra("costs")
//        total_costString = intent.getStringExtra("total_cost")
//        var constList = JSONArray()
//        try {
//            constList = JSONArray(costsString)
////            destinationsArrayList = ArrayList<CostsModel>()
////            destinationsArrayList!!.clear()
//            for (j in 0 until constList.length()) {
//                val constItem = constList.getJSONObject(j)
////                val model = CostsModel()
////                model.set_id(constItem.optString("_id"))
////                model.setName(constItem.optString("name"))
////                model.setCost(constItem.optString("cost"))
////                destinationsArrayList!!.add(model)
//            }
//            costJsonArray = constList
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }

    }

//    fun onAddFieldFill(name: String, cost: String) {
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val rowView = inflater.inflate(R.layout.field, null)
//        val currentName = rowView.text_edit_text //findViewById(R.id.text_edit_text)
//        val currentCost = rowView.number_edit_text//findViewById(R.id.number_edit_text)
//        currentName.setText(name)
//        currentCost.setText(cost)
//        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
//        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
//    }
//
//    fun onAddField(v: View) {
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val rowView = inflater.inflate(R.layout.field, null)
//        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)
//        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
//    }
//
//    fun onDelete(v: View) {
//        parentLinearLayout!!.removeView(v.parent as View)
//        // Log.d("LOG", "count >>>>>>>>> " + String.valueOf(Integer.parseInt(String.valueOf(parentLinearLayout.getChildCount())) - 1));
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_edit)
        mToolbar = findViewById(R.id.toolbar)
        mToolbar!!.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        mToolbar!!.setNavigationOnClickListener { onBackPressed() }
        mToolbar!!.title = "Edit Destination"
//        parentLinearLayout = findViewById(R.id.parent_linear_layout) as LinearLayout
        initUI()
//        val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener() {
//            fun onPlaceSelected(place: Place) {
//                // TODO: Get info about the selected place.
//                val placeAddress = place.getAddress().toString()
//                val placeLatLng = place.getLatLng()
//                address!!.setText(placeAddress)
//                latitude!!.setText(String.valueOf(placeLatLng.latitude))
//                longitude!!.setText(String.valueOf(placeLatLng.longitude))
//            }
//
//            fun onError(status: Status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: $status")
//            }
//
//        })
    }

//    @SuppressLint("LongLogTag", "SimpleDateFormat")
    private fun initUI() {
        getIntentData()

//        try {
//            for (j in 0 until costJsonArray.length()) {
//                val costItem = costJsonArray.getJSONObject(j)
//                onAddFieldFill(costItem.getString("name"), costItem.getString("cost"))
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }

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
//        location = findViewById(R.id.location) as EditText
//        description = findViewById(R.id.description) as EditText
//        latitude = findViewById(R.id.latitude) as EditText
//        longitude = findViewById(R.id.longitude) as EditText
//        address = findViewById(R.id.address) as EditText
//        distance = findViewById(R.id.distance) as EditText
//        note = findViewById(R.id.note) as EditText
//        costs = findViewById(R.id.costs) as EditText
//        total_cost = findViewById(R.id.total_cost) as EditText

        name!!.setText(nameString)
        image!!.setText(imageString)
        category!!.setText(categoryString)
        unit!!.setText(unitString)
        price!!.setText(priceString)
//        location!!.setText(locationString)
//        description!!.setText(descriptionString)
//        latitude!!.setText(latitudeString)
//        longitude!!.setText(longitudeString)
//        address!!.setText(addressString)
//        distance!!.setText(distanceString)
//        note!!.setText(noteString)
//        total_cost!!.setText(total_costString)

        // Toast.makeText(EditDestinationActivity.this, noteID, Toast.LENGTH_LONG).show();
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
        try {
            jobjContactDetails = JSONObject()
            jobjContactDetails.put("name", name!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("image", image!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("category", category!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("unit", unit!!.text.toString().trim { it <= ' ' })
            jobjContactDetails.put("price", price!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("location", location!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("description", description!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("latitude", latitude!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("longitude", longitude!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("address", address!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("distance", distance!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("note", note!!.text.toString().trim { it <= ' ' })
//            jobjContactDetails.put("costs", costList)
//            jobjContactDetails.put("total_cost", total_cost!!.text.toString().trim { it <= ' ' })
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
//        response.putJson(RestApis.KarmaGroups.vacapediaDestinations + "/" + id, finalJobjContactDetails)


        val service = ServiceVolley()
        val apiController = APIController(service)
        val path: String = "http://familygroceries.herokuapp.com/items"+"/"+id
//        val params = JSONObject()
//        params.put("name", "tempe")
//        params.put("image", "https://static.xx.fbcdn.net/rsrc.php/v3/yV/r/BhqIEprNoBN.png")
//        params.put("category", "0")
//        params.put("price", "2000")
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

            R.id.delete_tv -> {
                //hideKeyboard


                val service = ServiceVolley()
                val apiController = APIController(service)

//            val path: String = "http://familygroceries.herokuapp.com/groceries"
                val path: String = "http://familygroceries.herokuapp.com/items" + "/" + id


                apiController.delete(path) { response ->
                    // Parse the result
                    Log.d("TAG_HOME", response.toString())
                }
                finish()

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
        private val TAG = "EditDestinationActivity"
    }

}
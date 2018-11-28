package com.saladinid.blonjoan.activity

import android.R.style.Theme_Material_Light_Dialog_Alert
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
import com.saladinid.blonjoan.restpure.APIController
import com.saladinid.blonjoan.restpure.ServiceVolley
import kotlinx.android.synthetic.main.activity_destination_add.*
import org.json.JSONArray
import org.json.JSONObject

public class AddItemActivity: AppCompatActivity(), View.OnClickListener {

    internal
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
    var alertDialogBuilder: AlertDialog.Builder ? = null
    private
    var alertDialog: AlertDialog ? = null
    private
    var parentLinearLayout: LinearLayout ? = null

    private fun getIntentData() {
        val intent = this.intent
    }

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_add)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
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
        unit = findViewById(R.id.unit) as EditText
        price = findViewById(R.id.price) as EditText
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
            alertDialogBuilder = AlertDialog.Builder(this, Theme_Material_Light_Dialog_Alert)
        } else {
            alertDialogBuilder = AlertDialog.Builder(this)
        }
        val finalJobjContactDetails = jobjContactDetails
        Log.d(TAG, "finalJobjContactDetails: " + finalJobjContactDetails!!)
        val service = ServiceVolley()
        val apiController = APIController(service)
        val path: String = "http://familygroceries.herokuapp.com/items"
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
            val `in` = Intent(this@AddItemActivity, MainActivity::class.java)
            this@AddItemActivity.startActivity(`in`)
            this@AddItemActivity.finish()
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
        }
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private val TAG = "AddDestinationActivity"
    }

}
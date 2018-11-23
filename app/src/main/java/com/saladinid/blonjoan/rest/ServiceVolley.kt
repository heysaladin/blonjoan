package com.saladinid.blonjoan.rest

import android.util.Log
import android.view.textclassifier.TextLinks
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class ServiceVolley : ServiceInterface {

    val TAG = ServiceVolley::class.java.simpleName
    // val basePath = "https://your/backend/api/"

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.POST, /*basePath +*/ path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun get(path: String, completionHandler: (response: String?) -> Unit) {
        Log.d(TAG,  "path : $path ")
        val jsonObjReq = object : StringRequest(Method.GET, path,
                Response.Listener<String> { response ->
                    Log.d(TAG,  "response : $response ")
                },
                Response.ErrorListener { Log.d(TAG, "That didn't work!") }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
    override fun put(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.PUT, /*basePath +*/ path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/put request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/put request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun delete(path: String, completionHandler: (response: String?) -> Unit) {
        Log.d(TAG,  "path : $path ")
        val jsonObjReq = object : StringRequest(Method.DELETE, path,
                Response.Listener<String> {
//                    response ->
                    Log.d(TAG,  "response : OK ")
                },
                Response.ErrorListener { Log.d(TAG, "That didn't work!") }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

}

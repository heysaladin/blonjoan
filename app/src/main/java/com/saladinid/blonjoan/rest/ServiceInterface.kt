package com.saladinid.blonjoan.rest

import org.json.JSONArray
import org.json.JSONObject

interface ServiceInterface {
    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, completionHandler: (response: String?) -> Unit)
    fun put(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun delete(path: String, completionHandler: (response: String?) -> Unit)
}

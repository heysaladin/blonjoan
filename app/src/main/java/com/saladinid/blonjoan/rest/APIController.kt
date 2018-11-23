package com.saladinid.blonjoan.rest

import org.json.JSONArray
import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.post(path, params, completionHandler)
    }
    override fun get(path: String, completionHandler: (response: String?) -> Unit) {
        service.get(path, completionHandler)
    }
    override fun put(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.put(path, params, completionHandler)
    }
    override fun delete(path: String, completionHandler: (response: String?) -> Unit) {
        service.delete(path, completionHandler)
    }

}

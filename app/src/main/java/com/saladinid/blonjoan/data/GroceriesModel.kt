package com.saladinid.blonjoan.data

import org.json.JSONArray

class GroceriesModel {

    var _id: String? = null
    var title: String? = null
    var items: JSONArray? = null

    override fun toString(): String {
        return "GroceriesModel{" +
                "_id='" + _id + "\'" +
                ", title='" + title + "\'" +
                ", items='" + items + "\'" +
                "}"
    }

}

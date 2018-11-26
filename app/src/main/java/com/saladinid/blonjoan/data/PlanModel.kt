package com.codingdemos.vacapedia.data

import org.json.JSONArray

class PlanModel {

    var _id: String? = null
    var title: String? = null
    var content: String? = null
    var body_copy: String? = null
    var target_date: String? = null
    var target_time: String? = null
    var costs: JSONArray? = null
    var destinations: JSONArray? = null
    var image: String? = null
    var cost: String? = null
    var location: String? = null

    override fun toString(): String {
        return "UserModel{" +
                "_id='" + _id + "\'" +
                ", title='" + title + "\'" +
                ", content='" + content + "\'" +
                ", body_copy='" + body_copy + "\'" +
                ", target_date='" + target_date + "\'" +
                ", target_time='" + target_time + "\'" +
                ", costs='" + costs + "\'" +
                ", destinations='" + destinations + "\'" +
                ", image='" + image + "\'" +
                ", cost='" + cost + "\'" +
                ", location='" + location + "\'" +
                "}"
    }
}

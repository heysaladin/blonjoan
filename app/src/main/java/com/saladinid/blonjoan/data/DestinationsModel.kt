package com.codingdemos.vacapedia.data

import android.widget.EditText

class DestinationsModel {

    var name: String? = null
    var postID: String? = null
    var image: String? = null
    var menuID: String? = null
    var menuName: String? = null
    var _id: String? = null
    var category: String? = null
    var location: String? = null
    var description: String? = null
    var latitude: String? = null
    var longitude: String? = null
    var address: String? = null
    var distance: String? = null
    var note: String? = null
    var costs: String? = null
    var total_cost: String? = null

    override fun toString(): String {
        return "DestinationsModel{" +
                "_id='" + _id + "\'" +
                ", name='" + name + "\'" +
                ", postID='" + postID + "\'" +
                ", image='" + image + "\'" +
                ", menuID='" + menuID + "\'" +
                ", menuName='" + menuName + "\'" +
                ", category='" + category + "\'" +
                ", location='" + location + "\'" +
                ", description='" + description + "\'" +
                ", latitude='" + latitude + "\'" +
                ", longitude='" + longitude + "\'" +
                ", address='" + address + "\'" +
                ", distance='" + distance + "\'" +
                ", note='" + note + "\'" +
                ", costs='" + costs + "\'" +
                ", total_cost='" + total_cost + "\'" +
                "}"
    }

}

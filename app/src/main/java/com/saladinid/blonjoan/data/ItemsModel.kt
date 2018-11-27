package com.saladinid.blonjoan.data

class ItemsModel (
        _id: String,
        name: String,
        image: String,
        category: String,
        price: String
) {

    var _id: String? = _id
        get() = field
        set(value) { field = value }
    var name: String? = name
        get() = field
        set(value) { field = value }
    var image: String? = image
        get() = field
        set(value) { field = value }
    var category: String? = category
        get() = field
        set(value) { field = value }
    var price: String? = price
        get() = field
        set(value) { field = value }

    override fun toString(): String {
        return "ItemsModel{" +
                "_id='" + _id + "\'" +
                ", name='" + name + "\'" +
                ", image='" + image + "\'" +
                ", category='" + category + "\'" +
                ", price='" + price + "\'" +
                "}"
    }

}

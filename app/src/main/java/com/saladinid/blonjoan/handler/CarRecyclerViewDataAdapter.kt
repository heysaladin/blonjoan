package com.saladinid.blonjoan.handler

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.saladinid.blonjoan.R

class CarRecyclerViewDataAdapter(private val carItemList: List<CarRecyclerViewItem>?) : RecyclerView.Adapter<CarRecyclerViewItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarRecyclerViewItemHolder {
        // Get LayoutInflater object.
        val layoutInflater = LayoutInflater.from(parent.context)
        // Inflate the RecyclerView item layout xml.
        val carItemView = layoutInflater.inflate(R.layout.activity_card_view_item, parent, false)

        // Get car title text view object.
        val carTitleView = carItemView.findViewById<View>(R.id.card_view_image_title) as TextView
        // Get car image view object.
        val carImageView = carItemView.findViewById<View>(R.id.card_view_image) as ImageView
        // When click the image.
        carImageView.setOnClickListener {
            // Get car title text.
            val carTitle = carTitleView.text.toString()
            // Create a snackbar and show it.
            val snackbar = Snackbar.make(carImageView, "You click $carTitle image", Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        // Create and return our custom Car Recycler View Item Holder object.
        return CarRecyclerViewItemHolder(carItemView)
    }

    override fun onBindViewHolder(holder: CarRecyclerViewItemHolder, position: Int) {
        if (carItemList != null) {
            // Get car item dto in list.
            val carItem = carItemList[position]

            if (carItem != null) {
                // Set car item title.
                holder.carTitleText!!.text = carItem.carName
                // Set car image resource id.
                holder.carImageView!!.setImageResource(carItem.carImageId)
            }
        }
    }

    override fun getItemCount(): Int {
        var ret = 0
        if (carItemList != null) {
            ret = carItemList.size
        }
        return ret
    }
}

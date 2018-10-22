package com.saladinid.blonjoan.handler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.saladinid.blonjoan.R

class CarRecyclerViewItemHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    var carTitleText: TextView? = null
        private set

    var carImageView: ImageView? = null
        private set

    init {

        if (itemView != null) {
            carTitleText = itemView.findViewById<View>(R.id.card_view_image_title) as TextView

            carImageView = itemView.findViewById<View>(R.id.card_view_image) as ImageView
        }
    }
}

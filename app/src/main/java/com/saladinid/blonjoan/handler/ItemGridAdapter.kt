package com.saladinid.blonjoan.handler

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.activity.EditItemActivity
import com.saladinid.blonjoan.data.ItemsModel

class ItemGridAdapter(private val mContext: Context, private val mFlowerList: List < ItemsModel > ? ): RecyclerView.Adapter < FlowerViewHolder > () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row_item, parent, false)
        return FlowerViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val options = RequestOptions()
        options.centerCrop()
        options.placeholder(R.drawable.cabai)
        Glide.with(mContext)
                .load(mFlowerList!![position].image!!.replace(" ", "%20"))
                .apply(options)
                .into(holder.mImage)
        holder.mTitle.text = mFlowerList[position].name
        holder.tvDesc.text = mFlowerList[position].price
        holder.mCardView.setOnClickListener {
            /*
            val mIntent = Intent(mContext, EditItemActivity::class.java)
            mIntent.putExtra("_id", mFlowerList[holder.adapterPosition]._id)
            mIntent.putExtra("name", mFlowerList[holder.adapterPosition].name)
            mIntent.putExtra("image", mFlowerList[holder.adapterPosition].image)
            mIntent.putExtra("category", mFlowerList[holder.adapterPosition].category)
            mIntent.putExtra("unit", mFlowerList[holder.adapterPosition].unit)
            mIntent.putExtra("price", mFlowerList[holder.adapterPosition].price)
            mContext.startActivity(mIntent)
            */
            // Get car title text.
            val itemName = mFlowerList[position].name.toString()
            // Create a snackbar and show it.
            val snackbar = Snackbar.make(holder.mImage, "You click $itemName image", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    override fun getItemCount(): Int {
        return mFlowerList!!.size
    }

}

class FlowerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var mImage: ImageView
    var mTitle: TextView
    var tvDesc: TextView
    var mCardView: CardView

    init {
        mImage = itemView.findViewById(R.id.ivImage)
        mTitle = itemView.findViewById(R.id.tvTitle)
        tvDesc = itemView.findViewById(R.id.tvDesc)
        mCardView = itemView.findViewById(R.id.cardview)
    }

}
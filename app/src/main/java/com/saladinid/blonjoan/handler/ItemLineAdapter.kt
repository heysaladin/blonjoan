package com.saladinid.blonjoan.handler

import android.content.Context
import android.content.Intent
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
import com.saladinid.blonjoan.activity.EditGroceryActivity
import com.saladinid.blonjoan.data.GroceriesModel
import com.saladinid.blonjoan.data.ItemsModel

class ItemLineAdapter(private val mContext: Context, private val mFlowerList: List < ItemsModel > ? ): RecyclerView.Adapter < ItemLineViewHolder > () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLineViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row_item_inline, parent, false)
        return ItemLineViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ItemLineViewHolder, position: Int) {
        val options = RequestOptions()
        options.centerCrop()
        options.placeholder(R.drawable.cabai)
        Glide.with(mContext)
                .load(mFlowerList!![position].image!!.replace(" ", "%20"))
                .apply(options)
                .into(holder.mImage)
        holder.mTitle.text = mFlowerList!![position].name
        holder.mCardView.setOnClickListener {
            val mIntent = Intent(mContext, EditGroceryActivity::class.java)
            mIntent.putExtra("_id", mFlowerList[holder.adapterPosition]._id)
            mIntent.putExtra("title", mFlowerList[holder.adapterPosition].name)
            mIntent.putExtra("image", mFlowerList[holder.adapterPosition].image)
            mIntent.putExtra("category", mFlowerList[holder.adapterPosition].category)
            mIntent.putExtra("unit", mFlowerList[holder.adapterPosition].unit)
            mIntent.putExtra("price", mFlowerList[holder.adapterPosition].price)
            mContext.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return mFlowerList!!.size
    }

}

class ItemLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var mImage: ImageView
    var mTitle: TextView
    var mCardView: CardView

    init {
        mImage = itemView.findViewById(R.id.image)
        mTitle = itemView.findViewById(R.id.tvTitle)
        mCardView = itemView.findViewById(R.id.cardview)
    }

}
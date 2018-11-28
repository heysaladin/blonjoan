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

import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.activity.EditPlanActivity
import com.saladinid.blonjoan.data.GroceriesModel

class PlansLineAdapter(private val mContext: Context, private val mFlowerList: List < GroceriesModel > ? ): RecyclerView.Adapter < PlanLineViewHolder > () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanLineViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row_item_plan, parent, false)
        return PlanLineViewHolder(mView)
    }

    override fun onBindViewHolder(holder: PlanLineViewHolder, position: Int) {
        holder.mTitle.text = mFlowerList!![position].title
        holder.mCardView.setOnClickListener {
            val mIntent = Intent(mContext, EditPlanActivity::class.java)
            mIntent.putExtra("_id", mFlowerList[holder.adapterPosition]._id)
            mIntent.putExtra("title", mFlowerList[holder.adapterPosition].title)
            mIntent.putExtra("items", mFlowerList[holder.adapterPosition].items.toString())
            mContext.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return mFlowerList!!.size
    }

}

class PlanLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var mImage: ImageView
    var mTitle: TextView
    var mCardView: CardView

    init {
        mImage = itemView.findViewById(R.id.ivImage)
        mTitle = itemView.findViewById(R.id.tvTitle)
        mCardView = itemView.findViewById(R.id.cardview)
    }

}
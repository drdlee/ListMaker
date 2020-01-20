package com.raywenderlich.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListSelectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    // In kotlin constructor can declare within class declaration
    // dimana ListSelectionViewHolder memerlukan (itemView: View) sebagai parameter

    val listPosition = itemView.findViewById<TextView>(R.id.itemNumber)
    val listTitle = itemView.findViewById(R.id.itemString) as TextView
    // ada 2 cara untuk findViewById dengan kotlin, bisa lihat di atas
}
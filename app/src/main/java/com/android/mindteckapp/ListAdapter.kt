package com.android.mindteckapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private var originalItems: List<Pair<Pair<String, String>, Int>>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.itemTextViewTitle)
        val textViewSub: TextView = view.findViewById(R.id.itemTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
    private var items: List<Pair<Pair<String, String>, Int>> = originalItems // Maintain a mutable reference to the current list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (title, imageRes) = items[position]
        holder.textViewTitle.text = title.first
        holder.imageView.setImageResource(imageRes)
        holder.textViewSub.text = title.second

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newItems: List<Pair<Pair<String, String>, Int>>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun getList() :List<Pair<Pair<String, String>, Int>>{
        return items
    }
}

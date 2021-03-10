package com.hady.firstassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hady.firstassignment.databinding.ItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    val data = ArrayList<Item>()
    val db = FirebaseFirestore.getInstance()

    inner class ItemViewHolder(private val binding : ItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindNote(item: Item, position: Int) {
            binding.item = item
            binding.imgDelete.setOnClickListener {
                db.collection("contacts")
                    .document(item.id).delete()
                    .addOnCompleteListener {
                        data.removeAt(position)
                        notifyDataSetChanged()
                    }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : ItemBinding = DataBindingUtil.inflate(inflater, R.layout.item, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindNote(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
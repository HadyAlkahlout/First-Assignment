package com.hady.firstassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.hady.firstassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doSomething()
    }

    override fun onResume() {
        getItems()
        super.onResume()
    }

    private fun doSomething() {
        binding.isEmpty = true
        db = FirebaseFirestore.getInstance()
        adapter = ItemAdapter()
        binding.rcItems.adapter = adapter
        binding.rcItems.layoutManager = LinearLayoutManager(this)
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    private fun getItems() {
        db.collection("contacts")
            .get()
            .addOnCompleteListener { task ->
                if (task.result!!.documents.isNotEmpty()) {
                    adapter.data.clear()
                    for (i in task.result!!.documents) {
                        var item = Item(i.id, i.get("name") as String, i.get("number") as String, i.get("address") as String)
                        adapter.data.add(item)
                        adapter.notifyDataSetChanged()
                    }
                    binding.isEmpty = false
                }
            }
    }
}
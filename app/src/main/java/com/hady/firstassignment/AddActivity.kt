package com.hady.firstassignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.hady.firstassignment.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doSomething()
    }

    private fun doSomething() {
        db = FirebaseFirestore.getInstance()
        binding.btnSave.setOnClickListener {
            if (binding.tvName.text.isEmpty() || binding.tvNumber.text.isEmpty() || binding.tvAddress.text.isEmpty()) {
                Snackbar.make(binding.root, "Enter the empty field!", Snackbar.LENGTH_SHORT).show()
            } else {
                val map = HashMap<String, Any>()
                map["name"] = binding.tvName.text.toString()
                map["number"] = binding.tvNumber.text.toString()
                map["address"] = binding.tvAddress.text.toString()
                db.collection("contacts")
                    .add(map)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Item Added Successfully!!", Toast.LENGTH_SHORT)
                            .show()
                        onBackPressed()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Something Wrong Happen!!", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
    }
}
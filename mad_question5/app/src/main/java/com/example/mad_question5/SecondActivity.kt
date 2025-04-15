package com.example.mad_question5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_question5.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myData: MyData? = intent.getParcelableExtra("myData")
        myData?.let {
            binding.textView.text = "ID: ${it.id}, Name: ${it.name}"
        }
    }
}

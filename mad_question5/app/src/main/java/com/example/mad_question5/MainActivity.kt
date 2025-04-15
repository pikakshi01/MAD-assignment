package com.example.mad_question5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_question5.databinding.ActivityMainBinding
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myData = MyData(id = 1, name = "John Doe")
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("myData", myData)
        startActivity(intent)

        binding.textView.text = "Hello, World!"
    }
}

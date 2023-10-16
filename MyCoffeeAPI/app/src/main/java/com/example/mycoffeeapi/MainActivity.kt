package com.example.mycoffeeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.mycoffeeapi.databinding.ActivityMainBinding // to use XML binding
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var coffeeTitleURL = ""
    var coffeeImgURL = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for linking id attributes from activity_main to activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getCoffeeURL()
        Log.d("coffeeImgURL", "Coffee Image URL set")
        var button = findViewById<Button>(binding.coffeeBtn.id)
        var imageView = findViewById<ImageView>(binding.coffeeImg.id)
        var textView = findViewById<TextView>(binding.coffeeName.id)
        getNextImage(button, imageView,textView)
    }

    // gets Coffee pics from API
    private fun getCoffeeURL() {
        val client = AsyncHttpClient()
        // random id generator
        val randomNum = Random.nextInt(1,22)
        Log.d("Random id number", "id # ${randomNum}")
        client["https://api.sampleapis.com/coffee/hot/${randomNum}", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Coffee", "response successful")
                coffeeImgURL = json.jsonObject.getString("image")
                coffeeTitleURL = json.jsonObject.getString("title")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Coffee Error", errorResponse)
            }
        }]
    }

    // onClick Listener for coffee button
    private fun getNextImage(button: Button, imageView: ImageView, textView: TextView) {
        button.setOnClickListener{
            getCoffeeURL()
            textView.setText(coffeeTitleURL)
            Glide.with(this)
                .load(coffeeImgURL)
                .fitCenter()
                .into(imageView)
        }
    }
}
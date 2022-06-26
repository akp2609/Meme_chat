package com.example.memechat

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var url:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        takememe()
    }

    private fun takememe(){

        val memeImage = findViewById<ImageView>(R.id.memeimage)

        url = "https://meme-api.herokuapp.com/gimme"

        val progrss = findViewById<ProgressBar>(R.id.meme_load_progress)
        progrss.visibility = View.VISIBLE


// Request a string response from the provided URL.
        val JsonObjectRequestRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
            val url = response.getString("url")
                Glide.with(this).load(url).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progrss.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progrss.visibility = View.GONE
                        return false
                    }
                }).into(memeImage)
            },
            {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        Singleton.getInstance(this).addToRequestQueue(JsonObjectRequestRequest)
    }

    fun sendmeme(view: android.view.View) {
        val send = Intent(Intent.ACTION_SEND)
        send.type = "text/plain/image"
        send.putExtra(Intent.EXTRA_TEXT,"Hey try my new app i made for your daily dose of memes $url")
        val choose = Intent.createChooser(send,"share through...")
        startActivity(choose)

    }
    fun nextmeme(view: android.view.View) {
        takememe()
    }
}
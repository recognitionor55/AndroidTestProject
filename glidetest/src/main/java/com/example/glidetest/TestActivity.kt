package com.example.glidetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity(), IGlideManager{

    override lateinit var mRequestManager: RequestManager
    override lateinit var mGlide: Glide

    private var mAdapter: TestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        findViewById<Button>(R.id.clear_cache).setOnClickListener {
            mGlide.clearMemory()
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("jhlee", Thread.currentThread().name)
                mGlide.clearDiskCache()
            }
        }

        findViewById<Button>(R.id.activity).setOnClickListener {

        }

        createGlide(this)
        mRequestManager.applyDefaultRequestOptions(
            RequestOptions().diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC
            )
        )

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        mAdapter = TestAdapter(mRequestManager)
//

        rv.adapter = mAdapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("jhlee", "onDestroy")
        mRequestManager.pauseAllRequests()
    }
}
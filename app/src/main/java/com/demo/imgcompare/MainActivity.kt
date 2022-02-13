package com.demo.imgcompare


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.demo.imgcompare.adapter.ImageListAdapter
import com.demo.imgcompare.model.ImageModel
import com.demo.imgcompare.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.compare_table.*


class MainActivity : AppCompatActivity(), ImageListAdapter.OnCompareListener {

    lateinit var imageListAdapter: ImageListAdapter

    lateinit var mList: List<ImageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        imageListRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageListAdapter = ImageListAdapter(this)
        imageListRecyclerview.adapter = imageListAdapter
        imageListRecyclerview.setItemViewCacheSize(5000)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: MainActivityViewModel =
            ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if (it != null) {
                mList = it
                imageListAdapter.setImageList(it)
                imageListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in displaying List", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }

    override fun onCompare(position: Int) {
        setCompareTable(
            mList[position].id.toString(),
            mList[position].url.toString(),
            mList[position].title.toString(),
            mList[position].thumbnailUrl.toString()
        )
    }

    override fun onRemove(position: Int) {
        removeItem(mList[position].id.toString())
    }

    private fun setCompareTable(id: String, url: String, title: String, thumbnailURL: String) {
        val tableRow = TableRow(this)
        tableRow.setPadding(0, 0, 0, 5)
        val img = ImageView(this)
        img.setBackgroundColor(Color.WHITE)
        Glide.with(this)
            .load(thumbnailURL)
            .centerCrop().into(img)
        img.layoutParams = TableRow.LayoutParams(200, 200)
        tableRow.addView(img)
        val idTextView = TextView(this)
        idTextView.text = id
        idTextView.setTextColor(Color.parseColor("#44313D"))
        idTextView.gravity = Gravity.CENTER
        idTextView.width = 50
        tableRow.addView(idTextView)
        val urlTextView = TextView(this)
        urlTextView.text = url
        urlTextView.setTextColor(Color.parseColor("#44313D"))
        urlTextView.gravity = Gravity.CENTER
        urlTextView.width = 100
        tableRow.addView(urlTextView)
        val titleTextView = TextView(this)
        titleTextView.text = title
        titleTextView.setTextColor(Color.parseColor("#44313D"))
        titleTextView.gravity = Gravity.CENTER
        titleTextView.width = 100
        tableRow.addView(titleTextView)
        compare_table.addView(tableRow)
    }

    private fun removeItem(id: String) {
        var i = 0
        while (i < compare_table.childCount) {
            if (compare_table.getChildAt(i) != null) {
                val tableRow = compare_table.getChildAt(i) as TableRow
                val textView = tableRow.getChildAt(1) as TextView
                if (textView.text == id) {
                    compare_table.removeViewAt(i)
                }
            }
            i++
        }
    }

}
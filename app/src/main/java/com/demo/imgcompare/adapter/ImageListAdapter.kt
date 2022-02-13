package com.demo.imgcompare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.imgcompare.R
import com.demo.imgcompare.model.ImageModel
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImageListAdapter(
    private val onCompareListener: OnCompareListener
) :
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private var imageList: List<ImageModel>? = null

    fun setImageList(imageList: List<ImageModel>?) {
        this.imageList = imageList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList?.get(position)!!, position, onCompareListener)
    }

    override fun getItemCount(): Int {
        return if (imageList == null) 0
        else imageList?.size!!
    }

    class ViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val image = view.image
        private val imgTitle = view.img_title
        private val imgId = view.img_id
        private val imgUrl = view.img_url
        private val imgCompare = view.img_compare
        private val imgRemove = view.img_remove

        fun bind(
            data: ImageModel,
            position: Int,
            onCompareListener: OnCompareListener
        ) {
            imgTitle.text = data.title
            imgId.text = data.id
            imgUrl.text = data.url

            Glide.with(view.context)
                .load(data.thumbnailUrl)
                .centerCrop().into(image)

            imgCompare.setOnClickListener {
                onCompareListener.onCompare(position)
                imgCompare.visibility = View.GONE
                imgRemove.visibility = View.VISIBLE
            }

            imgRemove.setOnClickListener {
                onCompareListener.onRemove(position)
                imgCompare.visibility = View.VISIBLE
                imgRemove.visibility = View.GONE
            }
        }
    }

    interface OnCompareListener {
        fun onCompare(position: Int)
        fun onRemove(position: Int)
    }
}
package com.fani.produkappapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fani.produkappapi.R
import com.fani.produkappapi.model.ModelProduk

class ProdukAdapter (
    private val onClick : (ModelProduk) -> Unit
): ListAdapter<ModelProduk, ProdukAdapter.ProdukViewHolder>(ProdukCallBack){

    class ProdukViewHolder(itemview: View, val onClick: (ModelProduk) -> Unit):
        RecyclerView.ViewHolder(itemview){

        private val imgProduk: ImageView = itemview.findViewById(R.id.imgProduk)
        private val title : TextView = itemview.findViewById(R.id.title)
        private val brand : TextView = itemview.findViewById(R.id.brand)
        private val price : TextView = itemview.findViewById(R.id.price)

        //cel produk yang saat ini
        private var currentProduct : ModelProduk? = null

        init {
            itemview.setOnClickListener(){
                currentProduct?.let {
                    onClick(it)
                }
            }
        }
        fun bind(produk: ModelProduk){
            currentProduct = produk
            //set ke widget
            title.text= produk.title
            brand.text= produk.brand
            price.text= produk.price.toString()

            //untuk memanpilkan gambar pada widget (fetching gambar dengan glide)
            Glide.with(itemView).load(produk.thumbnail).centerCrop()
                .into(imgProduk)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product,
            parent, false
        )
        return ProdukViewHolder(view, onClick)

    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val produk = getItem(position)
        holder.bind(produk)

    }
}

object ProdukCallBack: DiffUtil.ItemCallback<ModelProduk>(){
    override fun areItemsTheSame(oldItem: ModelProduk, newItem: ModelProduk): Boolean {
        return oldItem ==newItem
    }

    override fun areContentsTheSame(oldItem: ModelProduk, newItem: ModelProduk): Boolean {
        return oldItem.id == newItem.id
    }
}
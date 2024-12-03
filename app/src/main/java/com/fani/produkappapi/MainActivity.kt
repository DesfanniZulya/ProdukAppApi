package com.fani.produkappapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fani.produkappapi.adapter.ProdukAdapter
import com.fani.produkappapi.model.ModelProduk
import com.fani.produkappapi.model.ResponseProduk
import com.fani.produkappapi.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<ResponseProduk>
    private lateinit var produkAdapter: ProdukAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.refresh_layout)
        recyclerView = findViewById(R.id.rv_products)

        // Initialize Adapter
        produkAdapter = ProdukAdapter { modelProduk: ModelProduk -> productOnClick(modelProduk) }
        recyclerView.adapter = produkAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set SwipeRefreshLayout listener
        swipeRefresh.setOnRefreshListener {
            getData()
        }

        // Load initial data
        getData()
    }

    private fun getData() {

        swipeRefresh.isRefreshing = false
        call = ApiClient.produkService.getAllProduk()
        call.enqueue(object : Callback<ResponseProduk> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseProduk>,
                response: Response<ResponseProduk>
            ) {
                swipeRefresh.isRefreshing = false
                if (response.isSuccessful){
                    produkAdapter.submitList(response.body()?.products)
                    produkAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseProduk>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }


        })
    }


    private fun productOnClick(modelProduk: ModelProduk) {
        // Handle product click
        // Example: Show a toast with product description
        Toast.makeText(
            applicationContext,
            modelProduk.description,
            Toast.LENGTH_SHORT
        ).show()
    }


}
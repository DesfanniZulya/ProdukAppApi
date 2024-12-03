package com.fani.produkappapi.service

import com.fani.produkappapi.model.ResponseProduk
import retrofit2.Call
import retrofit2.http.GET

interface ProdukService {

    @GET("products") //end point
    fun getAllProduk() : Call<ResponseProduk>
}
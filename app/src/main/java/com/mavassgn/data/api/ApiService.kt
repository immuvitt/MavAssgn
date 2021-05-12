package com.mavassgn.data.api

import com.mavassgn.data.model.AnimalsData
import com.mavassgn.utils.AppConstants.GET_DATA_URL
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(GET_DATA_URL)
    suspend fun getData(): Response<ArrayList<AnimalsData>>

}
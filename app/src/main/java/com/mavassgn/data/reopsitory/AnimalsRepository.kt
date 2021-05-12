package com.mavassgn.data.reopsitory

import com.mavassgn.data.api.ApiClient

class AnimalsRepository {

    suspend fun getProjects() =
        ApiClient.apiService.getData()
}
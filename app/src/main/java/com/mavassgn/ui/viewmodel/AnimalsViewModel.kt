package com.mavassgn.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mavassgn.data.model.AnimalsData
import com.mavassgn.data.reopsitory.AnimalsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimalsViewModel : ViewModel() {

    private var repository = AnimalsRepository()
    val error = MutableLiveData<String>()
    private var categoryLiveData = MutableLiveData<ArrayList<AnimalsData>>()
    private var subCategoryLiveData = MutableLiveData<ArrayList<String>>()

    private var mAnimals = ArrayList<AnimalsData>()
    private var subCategoryList = ArrayList<String>()

    init {

        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProjects()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    handleResponse(response.body())
                } else {
                    Log.e("Error", response.message())
                    onError("Something went wrong, Try again")
                }
            }
        }
    }

    private fun handleResponse(animals: ArrayList<AnimalsData>?) {
        if (animals != null) {
            mAnimals.addAll(animals)
            categoryLiveData.postValue(animals)
        }
    }

    fun getHeaders(): MutableLiveData<ArrayList<AnimalsData>> {
        return categoryLiveData
    }

    fun getListData(): MutableLiveData<ArrayList<String>> {
        return subCategoryLiveData
    }

    fun onPageChanged(position: Int) {
        subCategoryList.clear()
        subCategoryList.addAll(mAnimals[position].subcategory)
        subCategoryLiveData.postValue(subCategoryList)
    }

    private fun onError(message: String) {
        error.value = message
    }
}
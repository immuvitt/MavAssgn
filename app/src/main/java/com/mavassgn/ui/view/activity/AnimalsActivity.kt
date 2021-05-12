package com.mavassgn.ui.view.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mavassgn.R
import com.mavassgn.ui.view.adapter.HeaderAdapter
import com.mavassgn.ui.view.adapter.SubCategoryAdapter
import com.mavassgn.ui.viewmodel.AnimalsViewModel
import com.mavassgn.utils.UtilExtensions.showToast

class AnimalsActivity : AppCompatActivity() {

    private lateinit var viewModel: AnimalsViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var listAdapter: SubCategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchView: SearchView
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var toolbarSearch: SearchView
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals)

        initViews()
        setObservers()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewpager)
        recyclerView = findViewById(R.id.rv_list)
        searchView = findViewById(R.id.search_view)
        toolbarSearch = findViewById(R.id.toolbar_search_view)
        nestedScrollView = findViewById(R.id.nested_scroll_view)
        tabLayout = findViewById(R.id.tab_layout)

        //viewpager setup
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        headerAdapter = HeaderAdapter(this)
        viewPager.adapter = headerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewModel.onPageChanged(position)
            }

        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        }.attach()

        // recyclerview setup
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.isNestedScrollingEnabled = false;
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                linearLayoutManager.orientation
            )
        )

        listAdapter = SubCategoryAdapter(this)
        recyclerView.adapter = listAdapter


        //search view setup
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.getFilter().filter(newText)
                return false
            }

        })

        toolbarSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.getFilter().filter(newText)
                return false
            }

        })

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val isSearchViewVisible: Boolean = isVisible(v)
            if (isSearchViewVisible) {
                toolbarSearch.visibility = View.GONE
                searchView.visibility = View.VISIBLE
            } else {
                toolbarSearch.visibility = View.VISIBLE
                searchView.visibility = View.GONE
            }
        })


        //view model setup
        viewModel = ViewModelProvider(this).get(AnimalsViewModel::class.java)

    }

    private fun setObservers() {

        viewModel.getHeaders().observe(this, Observer { headers ->
            headerAdapter.setHeaderData(headers)
            headerAdapter.notifyDataSetChanged()
            viewModel.onPageChanged(0)
        })

        viewModel.getListData().observe(this, Observer { subcategories ->
            if (subcategories != null && subcategories.size != 0) {
                listAdapter.setListData(subcategories)
                listAdapter.notifyDataSetChanged()
            }

        })

        viewModel.error.observe(this, Observer { error ->
            showToast(error)
        })
    }

    private fun isVisible(view: View): Boolean {
        val scrollBounds = Rect()
        nestedScrollView.getDrawingRect(scrollBounds)
        var top = 0f
        var view1: View = view
        while (view1 is NestedScrollView) {
            top += view1.getY()
            view1 = view1.getParent() as View
        }
        val bottom: Float = top + view.height
        return scrollBounds.top < bottom && scrollBounds.bottom > top
    }


}
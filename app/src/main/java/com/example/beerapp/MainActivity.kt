package com.example.beerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.adapters.SearchAdapter
import com.example.beerapp.api.PunkApiRepository
import com.example.beerapp.pojo.Beer
import com.example.beerapp.ui.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val PunkApiRepository: PunkApiRepository = PunkApiRepository()
    private lateinit var beerList: MutableList<Beer>
    private lateinit var resultList: MutableList<Beer>
    lateinit var recyclerSearch: RecyclerView
    lateinit var adapter: SearchAdapter

    private var page: Int = 1
    private var isScrolling: Boolean = false

    lateinit var editSearch: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        beerList = arrayListOf()
        resultList = arrayListOf()


        setViews()
        getAllBeers()
        inflateRecycler()
        initListeners()


    }

    private fun initListeners() {
        editSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(name: String?): Boolean {
                if (name != null && isScrolling == false) {
                    beerList.clear()
                    searchBeers(name)
                }
                return true
            }

        })
    }

    fun searchBeers(name: String){
        CoroutineScope(Dispatchers.Main).launch {
            val call = PunkApiRepository.searchBeer(name)
            val beers = call.body()
            if (beers != null && call.code() == 200) {
                beerList.addAll(beers)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun getAllBeers() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = PunkApiRepository.getAllBeers(page)
            val beers = call.body()
            if (beers != null && call.code() == 200) {
                beerList.addAll(beers)
                page += 1
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun inflateRecycler() {
        adapter = SearchAdapter(beerList)
        recyclerSearch = findViewById(R.id.search_recycler)
        recyclerSearch.layoutManager = LinearLayoutManager(this)
        recyclerSearch.adapter = adapter
        adapter.setOnItemClickListner(object : SearchAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("id", beerList.get(position).id)
                startActivity(intent)
            }
        })

        recyclerSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling) {
                    isScrolling = false
                    getAllBeers()
                }
            }
        })
    }

    private fun setViews() {
        editSearch = findViewById(R.id.search_bar_home)
    }
}
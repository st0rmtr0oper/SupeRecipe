package com.example.superecipe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.superecipe.databinding.ActivitySearchBinding
import com.example.superecipe.logic.AppDataBase
import com.example.superecipe.logic.Recipe
import com.example.superecipe.logic.SearchAdapter

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var recipes: List<Recipe?>

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchField2.requestFocus()

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        var db = Room.databaseBuilder(this@SearchActivity, AppDataBase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        recipes = daoObject.getAll()!!

        setUpRecyclerView()

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.searchField2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != "") {
                    filterData(p0.toString())
                } else {
                    setUpRecyclerView()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    private fun filterData(filterText: String) {
        var filterData = ArrayList<Recipe>()
        for (i in recipes.indices) {
            if (recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())) {
                filterData.add(recipes[i]!!)
            }
            rvAdapter.filterList(filterList = filterData)
        }

    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.searchRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        for (i in recipes!!.indices) {
            dataList.add(recipes[i]!!)
        }
        rvAdapter = SearchAdapter(dataList, this)
        binding.searchRv.adapter = rvAdapter
    }
}

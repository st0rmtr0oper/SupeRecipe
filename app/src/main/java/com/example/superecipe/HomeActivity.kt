package com.example.superecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.superecipe.databinding.ActivityHomeBinding
import com.example.superecipe.logic.AppDataBase
import com.example.superecipe.logic.PopularAdapter
import com.example.superecipe.logic.Recipe

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        binding.searchField.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.saladsButton.setOnClickListener {
            var myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Salads")
            myIntent.putExtra("CATEGORY", "Salad")
            startActivity(myIntent)
        }
        binding.mainButton.setOnClickListener {
            var myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Main dishes")
            myIntent.putExtra("CATEGORY", "Dish")
            startActivity(myIntent)
        }
        binding.drinksButton.setOnClickListener {
            var myIntent =Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Drinks")
            myIntent.putExtra("CATEGORY", "Drinks")
            startActivity(myIntent)
        }
        binding.desertsButton.setOnClickListener {
            var myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Desserts")
            myIntent.putExtra("CATEGORY", "Desserts")
            startActivity(myIntent)
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.popularRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@HomeActivity, AppDataBase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")) {
                dataList.add(recipes[i]!!)
            }
            rvAdapter = PopularAdapter(dataList, this)
            binding.popularRv.adapter = rvAdapter
        }
    }
}
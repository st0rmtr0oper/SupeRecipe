package com.example.superecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.superecipe.databinding.ActivityCategoryBinding
import com.example.superecipe.logic.AppDataBase
import com.example.superecipe.logic.CategoryAdapter
import com.example.superecipe.logic.Recipe

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
//
//    private val binding by lazy {
//        ActivityCategoryBinding.inflate(layoutInflater)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        binding.textView12.text = intent.getStringExtra("TITLE")

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.categoryRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var db = Room.databaseBuilder(this@CategoryActivity, AppDataBase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)) {
                dataList.add(recipes[i]!!)
            }
            rvAdapter = CategoryAdapter(dataList, this)
            binding.categoryRv.adapter = rvAdapter
        }
    }
}
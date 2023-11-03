package com.example.superecipe.logic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.superecipe.RecipeActivity
import com.example.superecipe.databinding.SearchRvItemBinding

class SearchAdapter(var dataList: ArrayList<Recipe>, var context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

        inner class ViewHolder(var binding: SearchRvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SearchRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].img).into(holder.binding.searchImg)
        holder.binding.searchName.text = dataList[position].tittle
        holder.binding.searchElement.setOnClickListener{
            var intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("IMG",dataList[position].img)
            intent.putExtra("TTL",dataList[position].tittle)
            intent.putExtra("DES",dataList[position].des)
            intent.putExtra("ING",dataList[position].ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    fun filterList(filterList: ArrayList<Recipe>) {
        dataList=filterList
        notifyDataSetChanged()
    }
}

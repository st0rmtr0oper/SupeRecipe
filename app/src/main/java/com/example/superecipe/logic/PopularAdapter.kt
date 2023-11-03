package com.example.superecipe.logic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.superecipe.RecipeActivity
import com.example.superecipe.databinding.PopularRvItemBinding

class PopularAdapter(var dataList: ArrayList<Recipe>, var context: Context) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: PopularRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PopularRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].img).into(holder.binding.popularImg)
        holder.binding.popularName.text = dataList[position].tittle
        var time = dataList[position].ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        holder.binding.popularTime.text = time[0]
        holder.binding.popularItem.setOnClickListener{
            var intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("IMG",dataList[position].img)
            intent.putExtra("TTL",dataList[position].tittle)
            intent.putExtra("DES",dataList[position].des)
            intent.putExtra("ING",dataList[position].ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
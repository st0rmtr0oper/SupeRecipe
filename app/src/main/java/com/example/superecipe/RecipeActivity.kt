package com.example.superecipe

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.superecipe.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    var imgCrop = true
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("IMG")).into(binding.itemImg)

        binding.recipeTitle.text = intent.getStringExtra("TTL")

        binding.stepData.text = intent.getStringExtra("DES")

        var ing =
            intent.getStringExtra("ING")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }
                ?.toTypedArray()
        binding.time.text = ing?.get(0)
        binding.ingData.text = null
        for (i in 1 until ing!!.size) {
            binding.ingData.text = """
                ${binding.ingData.text}
                🔸 ${ing[i]}
            """.trimIndent()
        }

        binding.stepBtn.setBackgroundColor(Color.LTGRAY)
        binding.ingBtn.isClickable=false
        binding.stepScroll.isVisible=false

        binding.stepBtn.setOnClickListener {
            binding.stepBtn.setBackgroundColor(getColor(R.color.orange))
            binding.ingBtn.setBackgroundColor(Color.LTGRAY)
            binding.stepScroll.isVisible=true
            binding.ingScroll.isVisible=false
            binding.stepBtn.isClickable=false
            binding.ingBtn.isClickable=true
        }

        binding.ingBtn.setOnClickListener {
            binding.ingBtn.setBackgroundColor(getColor(R.color.orange))
            binding.stepBtn.setBackgroundColor(Color.LTGRAY)
            binding.ingScroll.isVisible=true
            binding.stepScroll.isVisible=false
            binding.ingBtn.isClickable=false
            binding.stepBtn.isClickable=true
        }

        binding.scaleBtn.setOnClickListener {

            if (imgCrop) {
                binding.itemImg.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("IMG")).into(binding.itemImg)
                //binding.scaleBtn.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility = View.INVISIBLE
                imgCrop = !imgCrop
            } else {
                binding.itemImg.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("IMG")).into(binding.itemImg)
                //binding.scaleBtn.setColorFilter(null)
                binding.shade.visibility = View.VISIBLE
                imgCrop = !imgCrop
            }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}
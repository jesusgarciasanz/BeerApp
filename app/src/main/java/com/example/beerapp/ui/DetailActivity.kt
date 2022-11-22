package com.example.beerapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.beerapp.R
import com.example.beerapp.api.PunkApiRepository
import com.example.beerapp.pojo.Beer
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var id: String

    private lateinit var buttonBack: LinearLayout
    private lateinit var beerTitle: TextView
    private lateinit var beerImage: ImageView
    private lateinit var beerAbv: TextView
    private lateinit var beerIbu: TextView
    private lateinit var beerTagline: TextView
    private lateinit var beerDescription: TextView
    private lateinit var buttonExtraInfo: ConstraintLayout

    private val PunkApiRepository: PunkApiRepository = PunkApiRepository()
    private lateinit var data: Beer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_detail)

        id = intent.getStringExtra("id").toString()

        setViews()
        getBeerById()
        initListeners()
    }
    private fun initListeners() {
        buttonBack.setOnClickListener {
            finish()
        }
        buttonExtraInfo.setOnClickListener{
            val intent = Intent(this, ExtraInfoActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun getBeerById() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                val call = PunkApiRepository.getDetailBeer(id)
                val content = call.body()
                if (content != null && call.isSuccessful && call.code() == 200) {
                    data = content[0]
                    if (data.name != null && call.code() == 200) {
                        beerTitle.text = data.name
                    }
                    if (data.tagline != null) {
                        beerTagline.text= data.tagline
                    }
                    if (data.abv != null) {
                        beerAbv.text = data.abv.toString()
                    }

                    if (data.ibu != null) {
                        beerIbu.text = data.ibu.toString()
                    }

                    if (data.description != null) {
                        beerDescription.text = data.description
                    }
                    if (data.image_url != null){
                        Picasso.get().load(data.image_url).fit().into(beerImage)
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            Toast.makeText(this@DetailActivity, "Error en la llamada", Toast.LENGTH_LONG).show()
        }
    }

    private fun setViews() {
        buttonBack = findViewById(R.id.button_back_all_results)
        beerTitle = findViewById(R.id.tv_detail_name)
        beerImage = findViewById(R.id.detail_beer_image)
        beerAbv = findViewById(R.id.detail_abv_text)
        beerIbu = findViewById(R.id.detail_ibu_text)
        beerTagline = findViewById(R.id.tv_tagline_detail)
        beerDescription = findViewById(R.id.tv_description_detail)
        buttonExtraInfo = findViewById(R.id.button_extra_info)
    }
}
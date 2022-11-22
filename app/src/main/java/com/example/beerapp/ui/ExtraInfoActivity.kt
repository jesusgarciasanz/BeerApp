package com.example.beerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.beerapp.R
import com.example.beerapp.api.PunkApiRepository
import com.example.beerapp.pojo.Beer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExtraInfoActivity : AppCompatActivity() {
    lateinit var id: String

    private val PunkApiRepository: PunkApiRepository = PunkApiRepository()
    private lateinit var data: Beer

    private lateinit var EiName: TextView
    private lateinit var EiFirstBrewed: TextView
    private lateinit var EiAbv: TextView
    private lateinit var EiIbu: TextView
    private lateinit var EiEbc: TextView
    private lateinit var EiPh: TextView
    private lateinit var EiAttenuation: TextView
    private lateinit var ButtonBackEi: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_extra_info)

        id = intent.getStringExtra("id").toString()

        setViews()
        getBeerById()
        initListeners()
    }

    private fun initListeners() {
        ButtonBackEi.setOnClickListener {
            finish()
        }
    }

    private fun getBeerById() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                val call = PunkApiRepository.getDetailBeer(id)
                val content = call.body()
                if (content != null && call.isSuccessful && call.code() == 200) {
                    data = content[0]
                    if (data.name != null) {
                        EiName.text = data.name
                    }
                    if (data.first_brewed != null) {
                        EiFirstBrewed.text = data.first_brewed
                    }
                    if (data.abv != null) {
                        EiAbv.text = data.abv.toString()
                    }
                    if (data.ibu != null) {
                        EiIbu.text = data.ibu.toString()
                    }
                    if (data.ebc != null) {
                        EiEbc.text = data.ebc.toString()
                    }
                    if (data.ph != null) {
                        EiPh.text = data.ph.toString()
                    }
                    if (data.attenuation_level != null) {
                        EiAttenuation.text = data.attenuation_level.toString()
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, "Error en la llamada", Toast.LENGTH_LONG).show()
        }
    }

    private fun setViews() {
        EiName = findViewById(R.id.header_beer_name)
        EiFirstBrewed = findViewById(R.id.text_first_brewed_ei)
        EiAbv = findViewById(R.id.text_abv_ei)
        EiIbu = findViewById(R.id.text_ibu_ei)
        EiEbc = findViewById(R.id.text_ebc_ei)
        EiPh = findViewById(R.id.text_ph_ei)
        EiAttenuation = findViewById(R.id.text_attenuation_ei)
        ButtonBackEi = findViewById(R.id.button_back_extra_info)
    }
}
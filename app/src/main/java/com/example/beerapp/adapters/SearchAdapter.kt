package com.example.beerapp.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.R
import com.example.beerapp.pojo.Beer
import com.squareup.picasso.Picasso

class SearchAdapter(val beerList: MutableList<Beer>) :
    RecyclerView.Adapter<SearchAdapter.ViewHoler>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHoler {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.search_card_layout, viewGroup, false)
        return ViewHoler(v, mListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHoler, position: Int) {
        val beer: Beer = beerList.get(position)
        viewHolder.itemTitle.text = beer.name
        viewHolder.itemTagline.text = beer.tagline
        Picasso.get().load(beer.image_url).fit().centerCrop().into(viewHolder.itemImage)

    }

    override fun getItemCount(): Int {
        return beerList.size
    }

    inner class ViewHoler(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView
        var itemTagline: TextView
        var itemImage: ImageView? =null

        init {
            itemTitle = itemView.findViewById(R.id.search_card_name)
            itemImage = itemView.findViewById(R.id.search_card_image)
            itemTagline = itemView.findViewById(R.id.search_card_tagline)

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }

        }
    }

}
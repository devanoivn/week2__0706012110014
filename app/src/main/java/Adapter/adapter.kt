package Adapter

import Tampilan.Inter
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2__0706012110014.R
import Array.ArrayHewan
import Data.Ayam
import Data.Sapi
import android.widget.Toast
import com.example.week2__0706012110014.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.tampilan.view.*
import com.example.week2__0706012110014.databinding.TampilanBinding
import kotlinx.android.synthetic.main.activity_input.view.*
import java.util.ArrayList

class adapter (var listlucu : ArrayList<ArrayHewan>, val tampilan: Inter) :
    RecyclerView.Adapter<adapter.vieww>() {
    private lateinit var viewBind: TampilanBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vieww {
        val layoutInflater = LayoutInflater.from(parent.context)
        val lihat = layoutInflater.inflate(R.layout.tampilan, parent, false)
        return vieww(lihat, tampilan)
    }

    override fun getItemCount(): Int {
        return listlucu.size
    }
    override fun onBindViewHolder(holder: vieww, position: Int) {
        holder.setData(listlucu[position])
    }
    class vieww(itemView: View, val cardListener: Inter): RecyclerView.ViewHolder(itemView) {

        fun setData(data: ArrayHewan){
            itemView.NamaCrad.text = data.nama
            itemView.suara.setOnClickListener {
                Toast.makeText(itemView.context, data.suara(), Toast.LENGTH_SHORT).show()
            }
            itemView.mamam.setOnClickListener {
                Toast.makeText(itemView.context, data.mamam(), Toast.LENGTH_SHORT).show()
            }
            if (data.addimage.isNotEmpty()) {
                itemView.animal_ImageView.setImageURI(Uri.parse(data.addimage))
            }
            itemView.delete.setOnClickListener {
                cardListener.delete(adapterPosition)
            }
            if (data is Ayam) {
                itemView.JenisCard.text = "Ayam"
            } else if (data is Sapi) {
                itemView.JenisCard.text = "Sapi"
            } else {
                itemView.JenisCard.text = "Kambing"
            }
            itemView.Card.setOnClickListener{
                cardListener.klikKartu(adapterPosition)
            }
            itemView.UsiaCard.text = "Umur : " + data.usia.toString() + " Tahun"
            itemView.ngedit.setOnClickListener {
                cardListener.klikKartu(adapterPosition)
            }

        }
    }
}
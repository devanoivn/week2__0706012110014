package com.example.week2__0706012110014

import Adapter.adapter
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import Array.ArrayHewan
import Data.Ayam
import Data.Base
import Data.Sapi
import Tampilan.Inter
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.week2__0706012110014.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.tampilan.view.*

class Detail : AppCompatActivity() {
    private lateinit var viewBind: ActivityDetailBinding
    private var letak: Int = -1
    private val hasil = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val foto = it.data?.data
            viewBind.infofoto.setImageURI(foto)
            Base.listDatahewan[letak].addimage = foto.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getfoto()
        menghapusdata()
    }

    override fun onResume() {
        val sapi = Base.listDatahewan[letak]
        super.onResume()
        look(sapi)
    }

    private fun getfoto(){
        letak = intent.getIntExtra("position", -1)
        val hewan = Base.listDatahewan[letak]
        if(!hewan.addimage.isEmpty())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.getContentResolver().takePersistableUriPermission(Uri.parse(Base.listDatahewan[letak].addimage),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }
        look(hewan)
    }

    private fun look(hewan: ArrayHewan) {
        viewBind.infofoto.setImageURI(Uri.parse(Base.listDatahewan[letak].addimage))
        viewBind.namainfo.text = hewan.nama
        viewBind.jenisinfo.setText("")
        viewBind.usiainfo.text = hewan.usia.toString()
    }
    private fun menghapusdata(){
        viewBind.hapusbutton.setOnClickListener {
            val delete = AlertDialog.Builder(this)
            delete.setTitle("Delete data")
            delete.setMessage("Apakah kamu ingin menghapus data ini?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            delete.setPositiveButton(android.R.string.yes) { function, which ->
                val hapusdata = Snackbar.make(viewBind.hapusbutton, "Data Deleted", Snackbar.LENGTH_INDEFINITE)
                hapusdata.setAction("Gagal") { hapusdata.dismiss() }
                hapusdata.setActionTextColor(Color.WHITE)
                hapusdata.setBackgroundTint(Color.GRAY)
                hapusdata.show()
                //remove
                Base.listDatahewan.removeAt(letak)
                finish()
            }
            delete.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            delete.show()
        }
        viewBind.toolbar.getChildAt(1).setOnClickListener {
            finish()
        }
        viewBind.floatingActionButton.setOnClickListener {
            val myIntent = Intent(this, Input::class.java).apply {
                putExtra("Position", letak)
            }
            startActivity(myIntent)
        }
    }
}
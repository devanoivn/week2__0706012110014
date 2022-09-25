package com.example.week2__0706012110014

import Adapter.adapter
import Array.ArrayHewan
import Data.Ayam
import Tampilan.Inter
import android.Manifest
import Data.Base
import Data.Kambing
import Data.Sapi
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.PopupMenu
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week2__0706012110014.databinding.ActivityHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_input.*

class Home : AppCompatActivity(), Inter{
    var modelMainList: ArrayList<ArrayHewan> = ArrayList()
    lateinit var ayam: Ayam
    lateinit var kambing: Kambing
    lateinit var sapi: Sapi
    lateinit var sapii: RadioButton
    lateinit var ayamm: RadioButton
    lateinit var kambingg: RadioButton
    private lateinit var viewBind:ActivityHomeBinding
    private val adapter = adapter(Base.listDatahewan, this)
    private var jumlahdata: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        Rv()
        datahewan()
//        setFilter()
        izin()
    }
    private fun jenis(jenis: String) {

        Base.filter.clear()

        if (jenis == "Ayam"){
            for (data in Base.listDatahewan) {
                if (data is Ayam) {
                    Base.filter.add(data)
                }
            }
        } else if (jenis == "Kambing") {
            for (data in Base.listDatahewan) {
                if (data is Kambing) {
                    Base.filter.add(data)
                }
            }
        } else {
            for (data in Base.listDatahewan) {
                if (data is Sapi) {
                    Base.filter.add(data)
                }
            }
        }
    }

    private fun datahewan(){
        viewBind.addhewan.setOnClickListener {
            val pindahinput = Intent(this, Input::class.java)
            startActivity(pindahinput)
        }
    }
    override fun klikKartu(position: Int) {
        val pindahinfo = Intent(this, Detail::class.java).apply {
            putExtra("position", position)
        }
        startActivity(pindahinfo)
    }   private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.itemId == R.id.sapii) {
                jenis("Sapi")
            } else if (menuItem.itemId == R.id.ayamm) {
                jenis("Ayam")
            } else if (menuItem.itemId == R.id.kambingg) {
                jenis("Kambing")
            }

            if (menuItem.itemId == R.id.All) {
                adapter.listlucu = Base.listDatahewan
            } else {
                adapter.listlucu = Base.filter
            }
            Rv()
            adapter.notifyDataSetChanged()

            if (menuItem.itemId == R.id.sapii) {
                setUp("Sapi")
            } else if (menuItem.itemId == R.id.ayamm) {
                setUp("Ayam")
            } else if (menuItem.itemId == R.id.kambingg) {
                setUp("Kambing")
            } else {
                setUp("hewan")
            }

            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }
    private fun setUp(str: String) {
        if (adapter.listlucu.size != 0) {
            imageView3.visibility = View.INVISIBLE
            tambah.visibility = View.INVISIBLE
        } else {
            imageView3.visibility = View.VISIBLE
            tambah.visibility = View.VISIBLE
            tambah.text = "Anda belum memiliki " + str
        }
    }

    override fun onResume() {
        super.onResume()
        jumlahdata = Base.listDatahewan.size
        if(jumlahdata == 0)
        {
            viewBind.imageView3.alpha = 1f
            viewBind.tambah.alpha = 1f
        }else
        {
            viewBind.tambah.alpha = 0f
            viewBind.imageView3.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }
    private fun Rv(){
        val Clean = GridLayoutManager(this,2)
        viewBind.list.layoutManager = Clean
        viewBind.list.adapter = adapter
    }

    override fun edit(position: Int) {
        addhewan.setOnClickListener {
        val myIntent = Intent(this, Input::class.java)
            startActivity(myIntent)

        }
        button.setOnClickListener {
            showMenu(it, R.menu.filter)
        }

    }
    private fun izin(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Base.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Base.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show()
        }
    }
    override fun delete(position: Int) {
        MaterialAlertDialogBuilder(this, com.google.android.material.R.style.MaterialAlertDialog_Material3)
            .setTitle("Hapus data")
            .setMessage("Apakah anda yakin ingin menghapus data ini")
            .setNegativeButton("Batal") { dialog, which ->
                Toast.makeText(this, "Anda membatalkan delete", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                dialog.cancel()
            }
            .setPositiveButton("Hapus") { dialog, which ->
                Base.listDatahewan.removeAt(position)
                Toast.makeText(this, "Data telah dihapus", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                onResume()
                dialog.cancel()
            }
            .show()
    }
//    private fun setFilter(){
//        filterr.setOnClickListener{
//            val dialogView = layoutInflater.inflate(R.menu.filter,null)
//
//            ayamm = dialogView.findViewById(R.id.ayamm)
//            sapii = dialogView.findViewById(R.id.sapii)
//            kambingg = dialogView.findViewById(R.id.kambingg)
//        }
//    }

}
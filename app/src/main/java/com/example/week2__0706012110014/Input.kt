package com.example.week2__0706012110014

import Data.Base
import Array.ArrayHewan
import Data.Ayam
import Data.Kambing
import Data.Sapi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.tampilan.*

class Input : AppCompatActivity() {

    private var position = -1
    private lateinit var urii: String
    private lateinit var animal: ArrayHewan

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data
            urii = uri.toString()// GET PATH TO IMAGE FROM GALLEY

            addimage.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        urii = ""
        inputdata()
        GetIntent()
        display()
    }

    private fun inputdata() {
        addimage.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        toolbar3.setOnClickListener() {
            finish()
        }

        button.setOnClickListener {
            var isCompleted = true

            if (namainput.editText?.text.toString().trim() == "") {
                isCompleted = false
                namainput.error = "Isi nama hewan terebih dahulu"
            } else {
                namainput.error = ""
            }


            if (jenishewan.checkedRadioButtonId == -1) {
                isCompleted = false
                Toast.makeText(this, "Isi Jenis hewan terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
            }

            if (usiainput.editText?.text.toString().trim() == "") {
                isCompleted = false
                usiainput.error = "Isi umur hewan terlebih dahulu"
            } else {
                usiainput.error = ""
            }

            if (isCompleted) {

                if (jenishewan.checkedRadioButtonId == R.id.radioButton) {
                    animal = Sapi(namainput.editText?.text.toString().trim(), usiainput.editText?.text.toString().trim().toInt(), "")
                } else if (jenishewan.checkedRadioButtonId == R.id.radioButton2){
                    animal = Ayam(namainput.editText?.text.toString().trim(), usiainput.editText?.text.toString().trim().toInt(), "")
                } else {
                    animal = Kambing(namainput.editText?.text.toString().trim(), usiainput.editText?.text.toString().trim().toInt(), "")
                }
                if (position == -1) {
                   add()
                } else {
                    edit()
                }

                finish()
            }
        }
    }
    private fun add(){
        Base.listDatahewan.add(animal)

        var index = Base.listDatahewan.size - 1

        if (urii.isNotEmpty()) {
            Base.listDatahewan[index].addimage = urii
        }

        Toast.makeText(applicationContext, "Berhasil menambahkan data hewan", Toast.LENGTH_SHORT).show()
    }
    private fun edit(){
        if (urii.isNotEmpty()) {
            Base.listDatahewan[position].addimage = urii
        }
        animal.addimage = Base.listDatahewan[position].addimage

        Base.listDatahewan.set(position, animal)
        val pindahinput = Intent(this, Home::class.java)
        startActivity(pindahinput)

        Toast.makeText(applicationContext, "Berhasil memperbarui data hewan", Toast.LENGTH_SHORT).show()
    }

    private fun GetIntent() {
        position = intent.getIntExtra("Position", -1)
    }

    private fun display() {
        if (position != -1) {
            namainput.editText!!.setText(Base.listDatahewan.get(position).nama)
            if (Base.listDatahewan.get(position) is Sapi) {
                jenishewan.check(R.id.radioButton)
            } else if (Base.listDatahewan.get(position) is Ayam) {
                jenishewan.check(R.id.radioButton2)
            } else {
                jenishewan.check(R.id.radioButton3)
            }
            usiainput.editText!!.setText(Base.listDatahewan.get(position).usia.toString())
            if (Base.listDatahewan.get(position).addimage != "") {
                addimage.setImageURI(
                    Uri.parse(
                        Base.listDatahewan.get(
                            position
                        ).addimage
                    )
                )
            }
            toolbar3.title = "Edit Hewan"
        }
    }
}
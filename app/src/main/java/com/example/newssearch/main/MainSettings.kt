package com.example.newssearch.main


import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.newssearch.R
import kotlinx.android.synthetic.main.layout_settings.*
import java.util.*
import kotlin.collections.ArrayList

class MainSettings : AppCompatActivity() {
    private var arraySortOrder: ArrayList<String>? = null
    private  var chuoi: String?=null
    private var beginDate: String?=null
    private var mDay: String? =null
    private var mThang: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_settings)

        initSpinner()
        setEvent()
    }
    private fun initSpinner(){
        arraySortOrder = arrayListOf()
        arraySortOrder?.add("newest")
        arraySortOrder?.add("oldest")
        arraySortOrder?.add("relevance")

        val array = ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySortOrder!!)
        array.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        snSortOrder.adapter=array
    }
    @SuppressLint("SetTextI18n")
    private fun setEvent(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        etBeginDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                etBeginDate.setText("$dayOfMonth/ $monthOfYear/ $year")
                if(dayOfMonth < 10){
                    mDay= "0$dayOfMonth"
                }
                if(monthOfYear <10){
                    mThang="0$monthOfYear"
                }

                beginDate = year.toString()+mThang+mDay
            }, year, month, day)
            dpd.show()
        }
        btnSave.setOnClickListener {
            // định dạng ngày tháng
            val sortOrder: String? = arraySortOrder?.get(snSortOrder.childCount).toString()
            var chuoi1 =""
            var chuoi2=""
            var chuoi3=""
            if (cbArts.isChecked)
                chuoi1 ="\"" +cbArts.text.toString()+"\""
            if (cbFashion.isChecked)
                chuoi2 ="\"Foreign\""
            if (cbSport.isChecked)
                chuoi3 ="\""+cbSport.text.toString()+"\""

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("beginDate", beginDate)
            intent.putExtra("sortOrder", sortOrder)

            if(chuoi1 =="" && chuoi2 =="" && chuoi3 =="" ){
              //  intent.putExtra("news","")
            }else{
                chuoi= "news_desk:($chuoi1 $chuoi2 $chuoi3)"
                intent.putExtra("news", chuoi!!.trim())
            }
            setResult(Activity.RESULT_OK,intent)
            finish()

        }
    }

}


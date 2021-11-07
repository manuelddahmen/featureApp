package com.android.example.cameraxbasic.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.example.cameraxbasic.R
import one.empty3.Main
import one.empty3.io.ProcessFile
import java.io.File

public class EffectsFragment : AppCompatActivity() {
    lateinit var effectList: ArrayList<ProcessFile>
    lateinit var auto: AutoCompleteTextView
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_effects)
        effectList = Main.initListProcesses()
        val l: List<String> = List<String>(effectList.size, init = {
            effectList[it].toString()
        })
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.select_dialog_item, l
        )
        auto = findViewById(R.id.effectsAutoCompleteTextView)
        auto.setThreshold(1)
        auto.setAdapter(adapter)



    }
    override fun onSaveInstanceState(outState : Bundle)
    {
        outState.putString("classname", auto.text.toString());
        super.onSaveInstanceState(outState);
    }

    fun process(strsFile: File)
    {
        //BitmapFactory.decodeFile(filePath)
    }
}

package one.empty3.feature.app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import one.empty3.Main
import one.empty3.io.ProcessFile
import android.widget.MultiAutoCompleteTextView
import androidx.core.content.ContextCompat
import java.io.File
import java.util.jar.Manifest

class ChooseEffectsActivity : Activity() {
    private var mediaFile: Uri? = null
    lateinit var autoCompleteTextView: MultiAutoCompleteTextView
    lateinit var editText: EditText
    lateinit var effectList: ArrayList<ProcessFile>
    lateinit var effectListStr: Array<String>
    lateinit var editText1: EditText

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("effects#logging", "create Effect Activity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_effects)
        init(savedInstanceState)
        mediaFile = intent.data
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun complete() {
        /*Log.i("autocomplete",
            "Autocomplete search")
        val split: List<String> = editText1.text.toString().split(",") as List<String>
        val effects = editText.text.toString()
        val splitEffectsList: List<String> = effects.split(",")
        var autoStr : String = ""
        for ((i, s1) in split.withIndex()) {
            Log.i("filters' list", ""+i+" : "+s1)
            if (s1.length > 2) {
                val count: Long = splitEffectsList.stream().filter { it.contains(s1) }
                    .count()
                if (count == 1L) {
                    Log.i("autocomplete",
                        "Autocomplete replacement: {"+s1+"} by ")
                    autoStr += splitEffectsList.stream().filter { it.contains(s1) }
                        .findFirst().toString()

                } else {
                    autoStr += s1
                }
            }
        }
        var eff = findViewById<EditText>(R.id.effectsToApply)
        eff.setText(autoStr)

        return

         */
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun init(savedInstanceState: Bundle?) {
        autoCompleteTextView =
            findViewById<MultiAutoCompleteTextView>(R.id.effectsAutoCompleteTextView)
        val effectList = Main.initListProcesses()
        effectListStr = Array<String>(effectList.size, { "" })
        var i = 0
        for (i in 0..effectListStr.size - 1) {
            effectListStr[i] =
                effectList[i].javaClass.canonicalName//.javaClass.simpleName.substring(
            //"class ".length, initListProcesses[i].javaClass.simpleName.lastIndexOf('.') )
        }
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, effectListStr)
        autoCompleteTextView.setAdapter(arrayAdapter)
        autoCompleteTextView.threshold = 2
        autoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        val applyEffectAction = findViewById<Button>(R.id.effectsToApply)
        applyEffectAction.setOnClickListener {
            run {
                intent = Intent(Intent.ACTION_EDIT)
                println("Cick on Effect button")
                intent.setDataAndType(mediaFile, "image/jpg")
                intent.setClass(
                    autoCompleteTextView.context,
                    Class.forName("one.empty3.feature.app.MyCameraActivity")
                )
                val file = File(mediaFile.toString())
                intent.putExtra(
                    "data", file
                )
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    val strEffectsList: String = autoCompleteTextView.text.toString()
                    var currentProcessFile: File = file
                    var currentOutputFile = currentProcessFile
                    var index = -1
                    strEffectsList.split(",").forEach {
                        val trim = it.trim()
                        if (effectListStr.contains(trim)) {
                            val indexOf: Int = effectListStr.indexOf(trim)
                            val processFile: ProcessFile = effectList.get(indexOf)

                            currentOutputFile = File(
                                currentProcessFile
                                    .absolutePath.substring(
                                        0, currentProcessFile
                                            .absolutePath.lastIndexOf(File.separator)
                                    ) + File.separator+trim+
                                        File.separator+currentOutputFile.name
                            )
                            processFile.process(currentProcessFile, currentOutputFile)
                            currentProcessFile = currentOutputFile

                            println("Effect class : " + trim)
                            println("In picture   : " + currentProcessFile)
                            println("Out  picture : " + currentOutputFile)

                        }
                    }
                    intent.data = Uri.fromFile(currentProcessFile)
                    intent.putExtra("data", currentProcessFile)
                    startActivity(intent)
                }
            }
        }

        /*
            Log.i("effects#logging", "init Details Effect Activity")
            effectList = Main.initListProcesses()
            editText = findViewById(R.id.editText)
            val l: List<String> = List<String>(effectList.size, init = {
                val s0: String = ((effectList[it]).javaClass.toString())
                val s: String = (editText.text.toString()
                        + s0 + ",")
                editText.setText(
                    s.subSequence(0, s.length), TextView.BufferType.EDITABLE
                ).toString()

            })

            editText1 = findViewById(R.id.effectsAutoCompleteTextView)
            editText1.setText(savedInstanceState?.getString("classname"))
            editText1.addTextChangedListener {
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        complete()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        complete()
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        complete()
                    }
                }
            }
      */
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("effects#logging", "save Effect Activity")
        outState.putString("classname", autoCompleteTextView.text.toString())
        super.onSaveInstanceState(outState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("effects#logging", "restore Effect Activity")
        val string = savedInstanceState?.getString("classname")
        autoCompleteTextView = findViewById(R.id.effectsAutoCompleteTextView)
        autoCompleteTextView.setText(string)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        Log.i("effects#logging", "destroy Effect Activity")
        super.onDestroy()
    }

}
